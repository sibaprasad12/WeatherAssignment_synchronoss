package synchronoss.assignment.weather.data

import synchronoss.assignment.weather.models.WeatherEntity
import synchronoss.assignment.weather.models.WeatherResponse


/**
 * Created by Sibaprasad Mohanty on 14/11/21.
 */

class UnitTestData {
    companion object {
        fun getWeatherList(): MutableList<WeatherEntity> {
            val list = ArrayList<WeatherEntity>()
            list.add(getMockWeather(1, "Dublin", "Cloudy", "9° C", "IE", "2021-11-10"))
            list.add(getMockWeather(2, "Blackrock", "Rainy", "7° C", "IE", "2021-11-11"))
            list.add(getMockWeather(3, "City Center", "Sunny", "8° C", "IE", "2021-11-12"))
            list.add(getMockWeather(4, "Dublin", "Cold", "8° C", "IE", "2021-11-13"))
            return list
        }

        fun getFavouriteWeatherData(): ArrayList<WeatherEntity> {
            val list = ArrayList<WeatherEntity>()
            list.add(getMockWeather(1, "Dublin", "Cloudy", "9° C", "IE", "2021-11-10"))
            list.add(getMockWeather(2, "Blackrock", "Rainy", "7° C", "IE", "2021-11-11"))
            list.add(getMockWeather(3, "City Center", "Sunny", "8° C", "IE", "2021-11-12"))
            list.add(getMockWeather(4, "Dublin", "Cold", "8° C", "IE", "2021-11-13"))
            return list
        }

        fun getMockWeatherResponse() = WeatherResponse("abc", 0, 0, 0, "Name", 0)
        fun getNullMockWeatherResponse() = null

        fun getMockWeather(
            id: Int,
            cityName: String,
            weatherType: String,
            temperature: String,
            country: String,
            date: String,
            isfavorite: Int = 1
        ): WeatherEntity {
            return WeatherEntity(
                id = 1,
                cityName = cityName,
                lat = 53.350140,
                lon = -6.266155,
                weatherType = weatherType,
                weatherDesc = weatherType,
                weatherIcon = "",
                temperature = temperature,
                country = country,
                date = date,
                isFavorite = isfavorite,
                minTemp = "", maxTemp = "", feelsLikeTemp = "",
                pressure = 123, humidity = 12, windSpeed = "", windDegree = "",
                windGust = "", cloudPossibility = "", sunRise = "", sunSet = "",
            )
        }
    }

}