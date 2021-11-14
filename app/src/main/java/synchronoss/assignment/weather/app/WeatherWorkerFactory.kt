package synchronoss.assignment.weather.app

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import synchronoss.assignment.weather.ui.repository.WeatherRepository
import synchronoss.assignment.weather.worker.UpdateWeatherWorker
import javax.inject.Inject

/**
 * Created by Sibaprasad Mohanty on 11/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

class WeatherWorkerFactory @Inject constructor(private val service: WeatherRepository) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return UpdateWeatherWorker(appContext, workerParameters, service)
    }
}