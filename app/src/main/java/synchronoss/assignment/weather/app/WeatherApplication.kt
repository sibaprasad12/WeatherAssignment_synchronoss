package synchronoss.assignment.weather.app

import android.app.Application
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


/**
 * Created by Sibaprasad Mohanty on 11/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

@HiltAndroidApp
class WeatherApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: WeatherWorkerFactory

    companion object {
        private val INSTANCE: WeatherApplication? = null
        fun get(): WeatherApplication? {
            return INSTANCE
        }
    }


    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()

    override fun onCreate() {
        super.onCreate()
    }


}