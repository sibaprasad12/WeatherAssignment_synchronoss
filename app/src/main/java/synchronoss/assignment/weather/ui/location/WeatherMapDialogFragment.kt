package synchronoss.assignment.weather.ui.location

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.dialogfragment_map.*
import synchronoss.assignment.weather.R
import synchronoss.assignment.weather.models.WeatherEntity

/**
 * Created by Sibaprasad Mohanty on 12/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

class WeatherMapDialogFragment : DialogFragment() {

    private lateinit var mapView: MapView
    private lateinit var imageViewBack: AppCompatImageView
    lateinit var textViewTitle: AppCompatTextView
    private var googleMap: GoogleMap? = null
    private lateinit var weatherLocation: LatLng
    var observeLatLng = MutableLiveData<LatLng>()

    companion object {
        val TAG = "WeatherMapDialogFragment"
        val LOCATION = "location"
        fun newInstance(
            location: LatLng
        ): WeatherMapDialogFragment = WeatherMapDialogFragment().apply {
            arguments = Bundle().apply {
                putParcelable(LOCATION, location)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialogfragment_map, container, false)
        mapView = view.findViewById(R.id.weatherMapView)
        imageViewBack = view.findViewById<AppCompatImageView>(R.id.imageViewBack)
        textViewTitle = view.findViewById<AppCompatTextView>(R.id.tvTitle)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewTitle.text = getString(R.string.select_location)
        mapView.onCreate(savedInstanceState)
        imageViewBack.setOnClickListener {
            dismissAllowingStateLoss()
        }
        weatherLocation = arguments?.getParcelable<LatLng>(LOCATION)
            ?: throw IllegalStateException("No args provided")
        setupMap()
        buttonSelectLocation.setOnClickListener {
            dismissAllowingStateLoss()
        }
        Toast.makeText(activity, "Tap on Map to select a location", Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }


    private fun setupMap() {
        mapView.visibility = View.VISIBLE
        mapView.onResume() // needed to get the map to display immediately
        try {
            activity?.applicationContext?.let { MapsInitializer.initialize(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mapView.getMapAsync(OnMapReadyCallback { mMap ->
            googleMap = mMap

            // For showing a move to my location button
            if (ActivityCompat.checkSelfPermission(
                    activity as FragmentActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    activity as FragmentActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
            }

            googleMap?.isMyLocationEnabled = true
            googleMap?.mapType = MAP_TYPE_NORMAL
            googleMap?.isMyLocationEnabled = true
            googleMap?.uiSettings?.isZoomControlsEnabled = true
            googleMap?.uiSettings?.isCompassEnabled = true
            googleMap?.uiSettings?.isMyLocationButtonEnabled = true
            googleMap?.addMarker(
                MarkerOptions().position(weatherLocation).title("My Location")
                    .snippet(
                        "Drag marker to choose ur location"
                    )
            )
            // For zooming automatically to the location of the marker
            val cameraPosition = CameraPosition.Builder().target(weatherLocation).zoom(12f).build()
            googleMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            mMap.setOnMapClickListener {
                googleMap?.clear()
                observeLatLng.value = LatLng(it.latitude, it.longitude)
                googleMap?.addMarker(
                    MarkerOptions().position(it).title("Selected Location")
                        .snippet(
                            "This is your selected Location"
                        )
                )
            }
        })
    }
}