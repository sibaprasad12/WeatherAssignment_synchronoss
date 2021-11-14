package synchronoss.assignment.weather.database

import androidx.room.*
import synchronoss.assignment.weather.models.WeatherEntity

/**
 * Created by Sibaprasad Mohanty on 12/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(WeatherEntity: WeatherEntity)

    @Delete
    suspend fun deleteWeather(WeatherEntity: WeatherEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWeather(WeatherEntity: WeatherEntity)

    @Query("SELECT * from WeatherEntity")
    suspend fun getAllSavedWeather(): List<WeatherEntity>


    @Query("SELECT * FROM WeatherEntity WHERE isFavorite > 0")
    suspend fun getAllFavoriteWeathers(): List<WeatherEntity>

    @Query("SELECT * FROM WeatherEntity ORDER BY weatherId DESC")
    suspend fun getAllWeatherByDate(): MutableList<WeatherEntity>

    @Query("SELECT * FROM WeatherEntity ORDER BY weatherId LIMIT 1")
    suspend fun getLastWeatherUpdated(): WeatherEntity

    @Query("SELECT COUNT(id) FROM WeatherEntity")
    suspend fun getRowCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWeathers(weatherList: List<WeatherEntity>)

    @Query("DELETE FROM WeatherEntity")
    suspend fun clearWeatherTable()


    @Query("SELECT * FROM weatherentity LIMIT :limit OFFSET :offset")
    suspend fun loadSavedWeatherByPage(limit: Int, offset: Int): List<WeatherEntity>

    @Query("SELECT * FROM weatherentity WHERE isFavorite > 0 LIMIT :limit OFFSET :offset")
    suspend fun getFavoriteWeatherByPage(limit: Int, offset: Int): List<WeatherEntity>

    @Query("SELECT * FROM weatherentity WHERE cityName =:cityName")
    suspend fun getWeatherByCityName(cityName: String): WeatherEntity?


}