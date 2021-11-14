package synchronoss.assignment.weather.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import synchronoss.assignment.weather.WeatherActivity
import synchronoss.assignment.weather.R


/**
 * Created by Sibaprasad Mohanty on 12/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

class NotificationUtils {
    companion object {
        private const val NOTIFICATION_ID = 22
        private const val CHANNEL_ID = "notify"
        private const val CHANNEL_NAME = "workmanager-reminder"

        fun sendNotification(context: Context) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel1 = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel1.enableVibration(true)
                channel1.enableLights(true)
                channel1.lightColor = R.color.purple_200
                channel1.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
                notificationManager.createNotificationChannel(channel1)
            }
            val builder: NotificationCompat.Builder = NotificationCompat.Builder(
                context,
                CHANNEL_ID
            )
                .setContentTitle("WorkManager Sample")
                .setContentText("WorkManager Started")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
            notificationManager.notify(1, builder.build())
        }

        fun makeWeatherStatusNotification(message: String?, context: Context) {

            // Make a channel if necessary
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Create the NotificationChannel, but only on API 26+ because
                // the NotificationChannel class is new and not in the support library
                val name: CharSequence = Constants.VERBOSE_NOTIFICATION_CHANNEL_NAME
                val description: String = Constants.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(CHANNEL_ID, name, importance)
                channel.description = description

                // Add the channel
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }

            // Create an explicit intent for an Activity in your app
            val intent = Intent(context, WeatherActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            // Create the notification
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(Constants.NOTIFICATION_TITLE)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setVibrate(LongArray(0))
                .setAutoCancel(true)

            // Show the notification
            NotificationManagerCompat.from(context)
                .notify(Constants.NOTIFICATION_ID, builder.build())
        }
    }
}