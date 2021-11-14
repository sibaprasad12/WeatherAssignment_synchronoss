package synchronoss.assignment.weather.database

import androidx.room.Database
import androidx.room.RoomDatabase
import synchronoss.assignment.weather.models.WeatherEntity

/**
 * Created by Sibaprasad Mohanty on 11/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

@Database(entities = [WeatherEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}