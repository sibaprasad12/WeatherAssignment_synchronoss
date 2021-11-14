package synchronoss.assignment.weather.network

import retrofit2.Response
import synchronoss.assignment.weather.models.WeatherResponse
import javax.inject.Inject

/**
 * Created by Sibaprasad Mohanty on 10/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun fetchWeatherByLocation(
        lat: Double,
        lon: Double
    ): Response<WeatherResponse> = apiService.fetchWeatherByLocation(lat, lon)

}