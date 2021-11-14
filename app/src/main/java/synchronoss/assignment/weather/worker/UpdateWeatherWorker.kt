package synchronoss.assignment.weather.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import synchronoss.assignment.weather.R
import synchronoss.assignment.weather.models.WeatherEntity
import synchronoss.assignment.weather.models.WeatherResponse
import synchronoss.assignment.weather.ui.repository.WeatherRepository
import synchronoss.assignment.weather.utils.NotificationUtils

/**
 * Created by Sibaprasad Mohanty on 10/11/21.
 */

@HiltWorker
class UpdateWeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val weatherRepository: WeatherRepository
) :
    CoroutineWorker(appContext, workerParams) {

    companion object {
        const val TAG = "UpdateWeatherWorker"
    }

    override suspend fun doWork(): Result {
        try {
            return try {
                Log.e(TAG, "Worker fetching data")
                CoroutineScope(Dispatchers.IO).launch {
                    val a = async { weatherRepository.getLastWeatherUpdate() }
                    val weatherEntity: WeatherEntity = a.await()
                    val lat = if (weatherEntity != null) weatherEntity.lat else 53.350140
                    val lon = if (weatherEntity != null) weatherEntity.lon else -6.266155
                    weatherRepository.fetchWeatherByLocation(lat, lon)
                        .let {
                            if (it.isSuccessful) {
                                val weatherResponse: WeatherResponse? = it.body()
                                Log.e(TAG, "Worker Fetched data")
                                weatherResponse?.let { weatherRes ->
                                    insertWeatherData(weatherRes)
                                    NotificationUtils.makeWeatherStatusNotification(
                                        applicationContext.getString(R.string.weather_updated),
                                        applicationContext
                                    )
                                }
                                Result.success()
                            } else {
                                Result.retry()
                            }
                        }
                }
                Result.success()
            } catch (e: Throwable) {
                e.printStackTrace()
                // Technically WorkManager will return Result.failure()
                // but it's best to be explicit about it.
                // Thus if there were errors, we're return FAILURE
                Log.e(TAG, "Error fetching data", e)
                Result.failure()
            }
        } catch (e: Exception) {
            if (runAttemptCount > 20) {
                Result.failure()
            }
            Result.retry()
        }
        return Result.success()
    }

    private fun insertWeatherData(weatherResponse: WeatherResponse) {
        val weather = weatherResponse.weather[0]
        val weatherEntity = WeatherEntity(
            weatherResponse.id,
            weatherResponse.name,
            weatherResponse.coord.lat,
            weatherResponse.coord.lon,
            weather.main,
            weather.description,
            weather.icon,
            weatherResponse.main.tempInCelcious,
            weatherResponse.main.temp_minInCelcious,
            weatherResponse.main.temp_maxInCelcious,
            weatherResponse.main.feelLikeInCelcious,
            weatherResponse.main.pressure,
            weatherResponse.main.humidity,
            weatherResponse.wind.speedFormatted,
            weatherResponse.wind.degreeFormatted,
            weatherResponse.wind.gustFormatted,
            weatherResponse.clouds.all.toString(),
            weatherResponse.dateTime,
            weatherResponse.sys.country,
            weatherResponse.sys.sunriseTime,
            weatherResponse.sys.sunsetTime,
            0
        )
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.i(TAG, "Inserted in worker")
                weatherRepository.updateWeather(weatherEntity)
            } catch (e: java.lang.Exception) {

            }
        }
    }
}
