package synchronoss.assignment.weather

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.work.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import synchronoss.assignment.weather.utils.CommonUtils


/**
 * Created by Sibaprasad Mohanty on 10/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE: Int = 111
    private var requiredPermission = arrayOf("android.permission.ACCESS_FINE_LOCATION")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    Snackbar.make(
                        rootContainer,
                        "Permission Granted, Now you can access location data",
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            ACCESS_FINE_LOCATION
                        )
                    ) {
                        // case 4 User has denied permission but not permanently
                        showMessageOKCancel(
                            getString(R.string.permission_not_granted)
                        ) { _, _ ->
                            requestPermissions(
                                arrayOf(ACCESS_FINE_LOCATION),
                                PERMISSION_REQUEST_CODE
                            )
                        }
                    } else {
                        // case 5. Permission denied permanently.
                        // You can open Permission setting's page from here now.
                        Snackbar.make(
                            rootContainer,
                            getString(R.string.permission_enied),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
                return
            }
            else -> {

            }
        }
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this@WeatherActivity)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    fun requestPermission() {
        CommonUtils.requestPermission(this, requiredPermission, PERMISSION_REQUEST_CODE)
    }

    fun setUpToolbar(title: String, homeEnable: Boolean) {
        val actionBar = supportActionBar
        actionBar?.title = title
        actionBar?.setDisplayHomeAsUpEnabled(homeEnable)
        actionBar?.setHomeButtonEnabled(homeEnable)
    }

}