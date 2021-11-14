package synchronoss.assignment.weather.base

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import synchronoss.assignment.weather.models.WeatherEntity
import synchronoss.assignment.weather.models.WeatherResponse


/**
 * Created by Sibaprasad Mohanty on 11/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

open class BaseViewModel : ViewModel(), Observable {

    var obsevableLoading = ObservableBoolean(false)
    var loadMore = ObservableBoolean(false)
    var errorMessage = ObservableField("")

    fun isValidWeatherData(weatherData: WeatherResponse?): Boolean {
        errorMessage.get()?.isNotEmpty()
        return (weatherData != null
                && !weatherData.name.isNullOrEmpty())
    }

    fun isValidWeatherataFromDatabase(weatherData: WeatherEntity?): Boolean {
        return (weatherData != null
                && !weatherData.cityName.isNullOrBlank())
    }

    fun isValidWeatherList(listWeather: List<WeatherEntity>) =
        listWeather.isNotEmpty()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}