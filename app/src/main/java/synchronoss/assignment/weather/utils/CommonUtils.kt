package synchronoss.assignment.weather.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar


/**
 * Created by Sibaprasad Mohanty on 12/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

class CommonUtils {

    companion object {

        fun showSnackbarMessage(view: View, message: String?, time: Int = Snackbar.LENGTH_SHORT) {
            val snackbar = Snackbar.make(view, message!!, Snackbar.LENGTH_SHORT)
            snackbar.show()
        }

        fun checkPermission(applicationContext: Context): Boolean {
            val result = ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            return result == PackageManager.PERMISSION_GRANTED
        }


        fun requestPermission(
            applicationContext: Activity,
            permissionArray: Array<String>,
            PERMISSION_REQUEST_CODE: Int
        ) {
            if (!checkPermission(applicationContext)) {
                ActivityCompat.requestPermissions(
                    applicationContext,
                    permissionArray,
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }
}