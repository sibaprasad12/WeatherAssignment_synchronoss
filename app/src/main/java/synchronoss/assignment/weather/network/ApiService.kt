package synchronoss.assignment.weather.network


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import synchronoss.assignment.weather.models.WeatherResponse

/**
 * Created by Sibaprasad Mohanty on 10/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

interface ApiService {
    @GET("weather")
    suspend fun fetchWeatherByLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("APPID") appid: String = "5ad7218f2e11df834b0eaf3a33a39d2a"
    ): Response<WeatherResponse>
}