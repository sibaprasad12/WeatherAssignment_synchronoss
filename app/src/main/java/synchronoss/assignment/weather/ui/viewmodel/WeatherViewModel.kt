package synchronoss.assignment.weather.ui.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import synchronoss.assignment.weather.base.BaseViewModel
import synchronoss.assignment.weather.models.WeatherEntity
import synchronoss.assignment.weather.models.WeatherResponse
import synchronoss.assignment.weather.network.ResponseState
import synchronoss.assignment.weather.ui.repository.WeatherRepository
import synchronoss.assignment.weather.utils.NetworkUtil
import javax.inject.Inject

/**
 * Created by Sibaprasad Mohanty on 12/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

@HiltViewModel
class WeatherViewModel @Inject constructor(
    @ApplicationContext private val application: Context?,
    private val weatherRepository: WeatherRepository
) :
    BaseViewModel() {

    var weatherData = ObservableField<WeatherEntity>()
    var weatherListFromDatabase = ObservableField<MutableList<WeatherEntity>>()


    fun fetchWeatherByLocation(context: Context, lat: Double, lon: Double) {
        obsevableLoading.set(true)
        try {
            if (NetworkUtil.isAvailable(context)) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        weatherRepository.fetchWeatherByLocation(lat, lon).let {
                            if (it.isSuccessful) {
                                val weatherResponse: WeatherResponse? = it.body()
                                weatherResponse?.let { weatherRes ->
                                    insertWeatherData(weatherRes)
                                    obsevableLoading.set(false)
                                    errorMessage.set("")
                                }
                            } else {
                                obsevableLoading.set(false)
                                errorMessage.set("Error Occurred!")
                            }
                        }
                    } catch (e: Exception) {
                        obsevableLoading.set(false)
                        errorMessage.set(e.message ?: "Error Occurred!")
                    }
                }
            } else {
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
                getLastLocationWeatherFromDatabase()
            }
        } catch (exception: Exception) {
            // emit(ResponseState.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun getWeatherFromDatabase() = liveData(Dispatchers.IO) {
        obsevableLoading.set(true)
        try {
            val weatherList = weatherRepository.getAllWeathers()
            emit(ResponseState.success(weatherList))
            obsevableLoading.set(false)
            errorMessage.set("")
        } catch (exception: Exception) {
            obsevableLoading.set(false)
            errorMessage.set(exception.message ?: "Error Occurred!")
            emit(ResponseState.error(null, exception.message ?: "Error Occurred!"))
        }
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
        weatherData.set(weatherEntity)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val weatherList = weatherRepository.insertWeather(weatherEntity)
                Log.i("TAG", "Inserted $weatherList")
                delay(1000)
            } catch (e: java.lang.Exception) {
                Log.i("TAG", "Data insert error")
            }
        }
    }

    fun getWeatherFromDatabaseByDateUpdate() {
        obsevableLoading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val weatherList = weatherRepository.getAllWeatherByDate()
                if (weatherList.isNotEmpty()) {
                    weatherListFromDatabase.set(weatherList)
                    obsevableLoading.set(false)
                    errorMessage.set("")
                } else {
                    obsevableLoading.set(false)
                    errorMessage.set("No weather data found")
                }
            } catch (exception: Exception) {
                obsevableLoading.set(false)
                errorMessage.set(exception.message ?: "Error Occurred!")
            }
        }
    }

    fun addToFavourite() {
        weatherData.get()?.isFavorite = 1
        CoroutineScope(Dispatchers.IO).launch {
            weatherData.get()?.let { weatherRepository.updateWeather(it) }
        }
    }

    private fun getLastLocationWeatherFromDatabase() {
        obsevableLoading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val lastUpdatedWeather = weatherRepository.getLastWeatherUpdate()
                obsevableLoading.set(false)
                if (isValidWeatherataFromDatabase(lastUpdatedWeather)) {
                    weatherData.set(lastUpdatedWeather)
                    errorMessage.set("")
                } else {
                    errorMessage.set("Error, Weather Data Not found")
                }
            } catch (e: java.lang.Exception) {
                obsevableLoading.set(false)
                errorMessage.set("No weather found from database")
            }
        }
    }

    fun clearWeatherData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                weatherRepository.clearWeatherData()
                weatherListFromDatabase.set(mutableListOf())
                errorMessage.set("Database Cleared")
            } catch (e: java.lang.Exception) {
                obsevableLoading.set(false)
                errorMessage.set("Database Error")
            }
        }
    }
}