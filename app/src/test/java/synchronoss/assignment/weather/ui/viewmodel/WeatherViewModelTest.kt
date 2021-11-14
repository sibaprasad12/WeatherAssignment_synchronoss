package synchronoss.assignment.weather.ui.viewmodel

import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
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