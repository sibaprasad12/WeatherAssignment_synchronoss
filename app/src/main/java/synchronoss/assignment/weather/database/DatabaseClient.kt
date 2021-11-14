package synchronoss.assignment.weather.database

import android.content.Context
import androidx.room.Room

/**
 * Created by Sibaprasad Mohanty on 11/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

class DatabaseClient(context: Context) {

    private var appDatabase: AppDatabase =
        Room
            .databaseBuilder(context.applicationContext, AppDatabase::class.java, "countries")
            .fallbackToDestructiveMigration()
            .build()


    companion object {
        private var mInstance: DatabaseClient? = null

        @Synchronized
        fun getInstance(mCtx: Context): DatabaseClient? {
            if (mInstance == null) {
                mInstance = DatabaseClient(mCtx)
            }
            return mInstance
        }
    }

    fun getAppDatabase() = appDatabase
}