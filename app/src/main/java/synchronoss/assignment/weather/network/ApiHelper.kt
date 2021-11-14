package synchronoss.assignment.weather.network

import retrofit2.Response
import synchronoss.assignment.weather.models.WeatherResponse

/**
 * Created by Sibaprasad Mohanty on 10/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

interface ApiHelper {
    suspend fun fetchWeatherByLocation(lat:Double, lon:Double): Response<WeatherResponse>
}