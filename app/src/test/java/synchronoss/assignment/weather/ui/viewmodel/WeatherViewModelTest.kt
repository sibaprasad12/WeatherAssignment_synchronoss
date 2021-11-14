package synchronoss.assignment.weather.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import synchronoss.assignment.weather.data.UnitTestData
import synchronoss.assignment.weather.models.WeatherEntity
import synchronoss.assignment.weather.ui.repository.WeatherRepository


@RunWith(JUnit4::class)
class WeatherViewModelTest {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var repository: WeatherRepository

    @Before
    @Throws(Exception::class)
    fun setUpBefore() {
        repository = mockk<WeatherRepository>()
        weatherViewModel = WeatherViewModel(null, repository)
    }

    @Test
    fun insertAndCheckList() {
        val mutableLiveData = MutableLiveData<WeatherEntity>()
        val weatherData =
            UnitTestData.getMockWeather(1, "Dublin", "Cloudy", "9° C", "IE", "2021-11-10")
        mutableLiveData.postValue(weatherData)
        assertEquals(weatherData.cityName, mutableLiveData.value?.cityName)
    }

    @Test
    fun check_if_weatherListIsvalid() {
        val weatherList = mutableListOf<WeatherEntity>()
        weatherList.add(
            UnitTestData.getMockWeather(
                1,
                "Dublin",
                "Cloudy",
                "9° C",
                "IE",
                "2021-11-10"
            )
        )
        weatherList.add(
            UnitTestData.getMockWeather(
                2,
                "City center",
                "Rainy",
                "9° C",
                "IE",
                "2021-11-10"
            )
        )
        weatherList.add(
            UnitTestData.getMockWeather(
                3,
                "Odisha",
                "Sunny",
                "9° C",
                "IE",
                "2021-11-10"
            )
        )
        assertTrue(weatherViewModel.isValidWeatherList(weatherList))
    }

    @Test
    fun check_if_weather_data_valid() {
        val weatherData = UnitTestData.getMockWeatherResponse()
        assertTrue(weatherViewModel.isValidWeatherData(weatherData))
    }

    @Test
    fun check_if_weather_data_is_notvalid() {
        val weatherData = UnitTestData.getNullMockWeatherResponse()
        assertTrue(weatherViewModel.isValidWeatherData(weatherData))
    }


    @After
    fun tearDown() {
    }


    @Test
    fun setObsevableLoading() {

    }


    @Test
    fun getWeatherFromDatabase() {
    }

    @Test
    fun getWeatherFromDatabaseByDateUpdate() {
    }

    @Test
    fun addToFavourite() {

    }

    @Test
    fun clearWeatherData() {

    }
}