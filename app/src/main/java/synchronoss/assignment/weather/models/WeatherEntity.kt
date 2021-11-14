package synchronoss.assignment.weather.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import synchronoss.assignment.weather.utils.DateUtils


/**
 * Created by Sibaprasad Mohanty on 12/11/21.
 */

@Parcelize
@Entity
data class WeatherEntity(
    @PrimaryKey val id: Int, val cityName: String, val lat: Double, val lon: Double, val weatherType: String,
    val weatherDesc: String, val weatherIcon: String, val temperature: String,
    val minTemp: String, val maxTemp: String, val feelsLikeTemp: String,
    val pressure: Int, val humidity: Int, val windSpeed: String, val windDegree: String,
    val windGust: String, val cloudPossibility: String, val date: String,
    val country: String, val sunRise: String, val sunSet: String,
    var isFavorite: Int
) : Parcelable {
    var lastUpdated: Long = System.currentTimeMillis()

    @Ignore
    val dateTime: String? = DateUtils.getDate(lastUpdated, "dd/MM/yyyy hh:mm:ss aa")
}