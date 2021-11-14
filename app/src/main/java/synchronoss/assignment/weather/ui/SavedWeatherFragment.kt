package synchronoss.assignment.weather.ui

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_saved_weather.*
import synchronoss.assignment.weather.WeatherActivity
import synchronoss.assignment.weather.R
import synchronoss.assignment.weather.callbacks.OnWeatherClickListener
import synchronoss.assignment.weather.databinding.FragmentSavedWeatherBinding
import synchronoss.assignment.weather.models.WeatherEntity
import synchronoss.assignment.weather.ui.adapter.WeatherAdapter
import synchronoss.assignment.weather.ui.location.WeatherMapDialogFragment
import synchronoss.assignment.weather.ui.viewmodel.WeatherViewModel
import synchronoss.assignment.weather.utils.CommonUtils


@AndroidEntryPoint
class SavedWeatherFragment : Fragment(), OnWeatherClickListener {


    private val viewModel by viewModels<WeatherViewModel>()
    lateinit var binding: FragmentSavedWeatherBinding
    private var isFavourite = 0
    private val adapter: WeatherAdapter by lazy {
        WeatherAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isFavourite = it.getInt("favourite_weather", 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            synchronoss.assignment.weather.R.layout.fragment_saved_weather,
            container,
            false
        )
        val view: View = binding.root
        binding.weatherAdapter = adapter
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        return view
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as WeatherActivity).setUpToolbar(
            getString(R.string.favorite_weathers),
            false
        )
        viewModel.getWeatherFromDatabaseByDateUpdate(isFavourite)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(synchronoss.assignment.weather.R.menu.weather_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                return true
            }
            R.id.menu_delete -> {
                viewModel.clearWeatherData()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onWeatherCLick(weatherEntity: WeatherEntity) {
        val action = SavedWeatherFragmentDirections.actionSavedweatherToWeather(weatherEntity)
        findNavController(this).navigate(action)
    }

    override fun onLocationClick(weatherEntity: WeatherEntity) {
        if (CommonUtils.checkPermission(requireContext())) {
            val weatherMapDialog =
                WeatherMapDialogFragment.newInstance(LatLng(weatherEntity.lat, weatherEntity.lon))
            weatherMapDialog.show(childFragmentManager, "TAG")
        } else {
            CommonUtils.showSnackbarMessage(rootLayout, "Location Permission not granted")
        }
    }
}