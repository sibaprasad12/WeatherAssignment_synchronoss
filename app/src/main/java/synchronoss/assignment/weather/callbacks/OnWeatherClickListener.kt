package synchronoss.assignment.weather.callbacks

import synchronoss.assignment.weather.models.WeatherEntity


/**
 * Created by Sibaprasad Mohanty on 11/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

interface OnWeatherClickListener {
    fun onWeatherCLick(weatherEntity: WeatherEntity)
    fun onLocationClick(weatherEntity: WeatherEntity)
}