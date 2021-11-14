package synchronoss.assignment.weather.utils


/**
 * Created by Sibaprasad Mohanty on 12/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

class Constants {

    private constructor()

    companion object{

        // Name of Notification Channel for verbose notifications of background work
        val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence = "Verbose WorkManager Notifications"
        const val KEY_OUTPUT_DATA = "KEY_OUTPUT_DATA"
        var VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION = "Shows notifications whenever work starts"
        val NOTIFICATION_TITLE: CharSequence = "Weather Tracker"
        const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
        const val NOTIFICATION_ID = 1

        // The name of the Sync Data work
        const val SYNC_DATA_WORK_NAME = "sync_data_work_name"


        // Other keys
        const val DELAY_TIME_MILLIS: Long = 3000

        const val TAG_SYNC_DATA = "TAG_SYNC_WEATHER_DATA1"
        const val TAG_SYNC_WEATHER_DATA = "TAG_SYNC_WEATHER_DATA"

        // Ensures this class is never instantiated
        private fun Constants() {}
    }

}