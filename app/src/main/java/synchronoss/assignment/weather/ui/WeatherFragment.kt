package synchronoss.assignment.weather.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.work.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_weather.*
import synchronoss.assignment.weather.WeatherActivity
import synchronoss.assignment.weather.R
import synchronoss.assignment.weather.databinding.FragmentWeatherBinding
import synchronoss.assignment.weather.ui.location.WeatherMapDialogFragment
import synchronoss.assignment.weather.ui.viewmodel.WeatherViewModel
import synchronoss.assignment.weather.utils.CommonUtils
import synchronoss.assignment.weather.utils.Constants
import synchronoss.assignment.weather.worker.UpdateWeatherWorker
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Sibaprasad Mohanty on 12/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel by viewModels<WeatherViewModel>()
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLat: Double = 0.0
    private var currentLong: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fetcWeatherhDataInBackground()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
        val view: View = binding.root
        setHasOptionsMenu(true)
        binding.viewModel = viewModel
        return view
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as WeatherActivity).setUpToolbar(getString(R.string.weather_details), false)
        fetchLatestLocation()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.weather_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_refresh -> {
                viewModel.fetchWeatherByLocation(
                    requireActivity(),
                    currentLat,
                    currentLong
                )
                super.onOptionsItemSelected(item)
            }
            R.id.menu_favourite -> {
                viewModel.addToFavourite()
                super.onOptionsItemSelected(item)
            }
            R.id.menu_location -> {
                showMapTogetWeatherByLocation()
                super.onOptionsItemSelected(item)
            }
            R.id.menu_weather -> {
                val action =
                    WeatherFragmentDirections
                        .actionLauncherToSavedweather()
                findNavController(this).navigate(action)
                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fetcWeatherhDataInBackground() {
        // Create Network constraint
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val periodicSyncDataWork =
            PeriodicWorkRequest.Builder(UpdateWeatherWorker::class.java, 15, TimeUnit.MINUTES)
                .addTag(Constants.TAG_SYNC_WEATHER_DATA)
                .setConstraints(constraints) // setting a backoff on case the work needs to retry
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()
        WorkManager.getInstance(requireActivity().application).enqueueUniquePeriodicWork(
            Constants.TAG_SYNC_WEATHER_DATA,
            ExistingPeriodicWorkPolicy.KEEP,  //Existing Periodic Work policy
            periodicSyncDataWork //work request
        )
    }

    private fun showMapTogetWeatherByLocation() {
        if (CommonUtils.checkPermission(requireContext())) {
            val weatherMapDialog =
                WeatherMapDialogFragment.newInstance(LatLng(currentLat, currentLong))
            weatherMapDialog.show(childFragmentManager, "TAG")
            weatherMapDialog.observeLatLng.observe(viewLifecycleOwner, {
                viewModel.fetchWeatherByLocation(requireActivity(), it.latitude, it.longitude)
            })
        } else {
            Toast.makeText(requireActivity(), "Location Permission not granted", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun fetchLatestLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            AlertDialog.Builder(requireActivity())
                .setMessage(getString(R.string.permission_not_granted))
                .setPositiveButton("OK") { _, _ ->
                    (requireActivity() as WeatherActivity).requestPermission()
                }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location == null) {
                    CommonUtils.showSnackbarMessage(rootLayout, "Location Not found")
                } else {
                    if (currentLat != location.latitude && currentLong != location.longitude) {
                        viewModel.fetchWeatherByLocation(
                            requireActivity(),
                            location.latitude,
                            location.longitude
                        )
                    }
                    currentLat = location.latitude
                    currentLong = location.longitude
                }
            }
    }
}