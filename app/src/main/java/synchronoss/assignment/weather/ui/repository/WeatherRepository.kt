package synchronoss.assignment.weather.ui.repository

import synchronoss.assignment.weather.database.WeatherDao
import synchronoss.assignment.weather.models.WeatherEntity
import synchronoss.assignment.weather.network.ApiHelperImpl
import javax.inject.Inject


/**
 * Created by Sibaprasad Mohanty on 12/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

class WeatherRepository @Inject constructor(
    private val apiHelper: ApiHelperImpl,
    private val weatherDao: WeatherDao
) {

    suspend fun fetchWeatherByLocation(lat: Double, lon: Double) =
        apiHelper.fetchWeatherByLocation(lat, lon)

    // database operations
    suspend fun getAllWeathers() = weatherDao.getAllSavedWeather()
    suspend fun insertWeather(weather: WeatherEntity) = weatherDao.insertWeather(weather)
    suspend fun insertAllWeather(weathers : List<WeatherEntity>) = weatherDao.insertAllWeathers(weathers)
    suspend fun getLastWeatherUpdate() = weatherDao.getLastWeatherUpdated()
    suspend fun getAllFavouriteWeather() = weatherDao.getAllFavoriteWeathers()
    suspend fun getAllWeatherByDate() = weatherDao.getAllWeatherByDate()
    suspend fun getAllWeatherBycityName(cityName:String) = weatherDao.getWeatherByCityName(cityName)
    suspend fun updateWeather(weather: WeatherEntity) = weatherDao.updateWeather(weather)
    suspend fun clearWeatherData() = weatherDao.clearWeatherTable()
}