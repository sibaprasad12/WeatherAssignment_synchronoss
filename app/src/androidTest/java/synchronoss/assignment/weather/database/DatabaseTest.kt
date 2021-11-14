package synchronoss.assignment.weather.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import synchronoss.assignment.weather.data.AndroidTestData
import synchronoss.assignment.weather.models.WeatherEntity
import java.io.IOException

/**
 * Created by Sibaprasad Mohanty on 14/11/21.
 */

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class TvShowDatabaseTest : TestCase() {

    private lateinit var weatherDao: WeatherDao
    private lateinit var db: AppDatabase
    lateinit var context: Context

    @Before
    @Throws(Exception::class)
    fun setUpBefore() {
        context = mockk<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        weatherDao = db.weatherDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadWeatherData() = runBlocking {
        val cityName = "Dublin"
        val weatherInsert: WeatherEntity =
            AndroidTestData.getMockWeather(1, cityName = cityName, "", ",", "", "")
        weatherDao.insertWeather(weatherInsert)
        val weatherEntity = weatherDao.getWeatherByCityName(cityName)
        assertThat(weatherInsert.cityName == weatherEntity?.cityName).isTrue()
    }

    @Test
    @Throws(Exception::class)
    fun readWriteWeatherTest() = runBlocking {
        try {
            val cityName = "Dublin"
            val weatherInsert: WeatherEntity =
                AndroidTestData.getMockWeather(1, cityName = cityName, "", ",", "", "")
            weatherDao.insertWeather(weatherInsert)
            val weatherEntity = weatherDao.getWeatherByCityName(cityName)
            assertThat(weatherInsert.cityName == weatherEntity?.cityName).isTrue()
        } catch (e: java.lang.Exception) {
            Log.i("WeatherViewModelUiTest", e.message!!)
        }
    }

    @Test
    @Throws(Exception::class)
    fun readWriteWeatherListContains() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cityName = "Dublin"
                val weatherInsert: WeatherEntity =
                    AndroidTestData.getMockWeather(1, cityName = cityName, "", ",", "", "")
                weatherDao.insertWeather(weatherInsert)
                val weatherList = weatherDao.getAllSavedWeather()
                assertTrue(weatherList.contains(weatherInsert))
            } catch (e: java.lang.Exception) {
                Log.i("WeatherViewModelUiTest", e.message!!)
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun insert_element_to_favourite_list_and_check_if_exists_or_not() = runBlocking {
        try {
            val cityName = "Dublin"
            val weatherInsert: WeatherEntity =
                AndroidTestData.getMockWeather(1, cityName = cityName, "", ",", "", "")
            weatherDao.insertWeather(weatherInsert)
            val favoriteWeatherList = weatherDao.getAllFavoriteWeathers()
            assertFalse(favoriteWeatherList.contains(weatherInsert))
        } catch (e: java.lang.Exception) {
            Log.i("WeatherViewModelUiTest", e.message!!)
        }
    }

    @Test
    @Throws(Exception::class)
    fun insert_element_to_favourite_list_and_check_exists() = runBlocking {
        try {
            weatherDao.clearWeatherTable()
            val weatherList = weatherDao.getAllFavoriteWeathers()
            assertTrue(weatherList.isEmpty())
        } catch (e: java.lang.Exception) {
            Log.i("WeatherViewModelUiTest", e.message!!)
        }
    }

    @Test
    @Throws(Exception::class)
    fun remove_all_weather_return_empty_favoutite_weatherList() = runBlocking {
        try {
            weatherDao.clearWeatherTable()
            val weatherList = weatherDao.getAllSavedWeather()
            assertTrue(weatherList.isEmpty())
        } catch (e: java.lang.Exception) {
            Log.i("WeatherViewModelUiTest", e.message!!)
        }
    }

    @Test
    @Throws(Exception::class)
    fun insert_duplicate_weather_return_error() = runBlocking {
        try {
            weatherDao.clearWeatherTable()
            val cityName = "Dublin"
            val weatherInsert: WeatherEntity =
                AndroidTestData.getMockWeather(1, cityName = cityName, "", ",", "", "")
            weatherDao.insertWeather(weatherInsert)
            weatherDao.insertWeather(weatherInsert)
            val weatherList = weatherDao.getAllFavoriteWeathers()
            assertTrue(weatherList.size == 1)
        } catch (e: java.lang.Exception) {
            Log.i("WeatherViewModelUiTest", e.message!!)
            assertTrue(true)
        }
    }

    @Test
    fun testFetchWeathersByPage() = runBlocking {
        try {
            val limit = 3
            weatherDao.clearWeatherTable()
            val weatherList = AndroidTestData.getMockedWeatherList()
            weatherDao.insertAllWeathers(weatherList)

            val weatherListFromDatabase = weatherDao.getFavoriteWeatherByPage(limit, 0)
            assertTrue(weatherListFromDatabase.size == 3)
        } catch (e: java.lang.Exception) {
            Log.i("WeatherViewModelUiTest", e.message!!)
        }
    }

    @Test
    fun testGetAllFavouriteWeathers() = runBlocking {
        try {
            weatherDao.clearWeatherTable()
            val favoriteWeathers = AndroidTestData.getMockedWeatherList()
            weatherDao.insertAllWeathers(favoriteWeathers)

            val favouriteListFromDatabase = weatherDao.getAllFavoriteWeathers()
            assertTrue(favoriteWeathers.size == favouriteListFromDatabase.size)
        } catch (e: java.lang.Exception) {
            Log.i("WeatherViewModelUiTest", e.message!!)
        }
    }
}

