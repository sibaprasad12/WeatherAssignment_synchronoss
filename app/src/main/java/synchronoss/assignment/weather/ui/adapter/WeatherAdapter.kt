package synchronoss.assignment.weather.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import synchronoss.assignment.weather.BR
import synchronoss.assignment.weather.callbacks.OnWeatherClickListener
import synchronoss.assignment.weather.databinding.ItemviewWeatherBinding
import synchronoss.assignment.weather.models.WeatherEntity

/**
 * Created by Sibaprasad Mohanty on 12/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

class WeatherAdapter(
    private val onWeatherClickListener: OnWeatherClickListener
) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private var weatherList: MutableList<WeatherEntity> = ArrayList()

    class WeatherViewHolder(private val binding: ItemviewWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: WeatherEntity, listener: OnWeatherClickListener) {
            binding.setVariable(BR.weather, obj)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val weatherBinding: ItemviewWeatherBinding =
            ItemviewWeatherBinding.inflate(inflater, parent, false)
        return WeatherViewHolder(weatherBinding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item, onWeatherClickListener)
    }

    override fun getItemCount() = differ.currentList.size

    private val WEATHER_DIFF_CALLBACK: DiffUtil.ItemCallback<WeatherEntity> =
        object : DiffUtil.ItemCallback<WeatherEntity>() {
            override fun areItemsTheSame(
                @NonNull oldWeather: WeatherEntity,
                @NonNull newWeather: WeatherEntity
            ): Boolean {
                return oldWeather.weatherId == newWeather.weatherId
            }

            override fun areContentsTheSame(
                @NonNull oldWeather: WeatherEntity,
                @NonNull newWeather: WeatherEntity
            ): Boolean {
                return oldWeather.weatherId == newWeather.weatherId
            }
        }

    private val differ: AsyncListDiffer<WeatherEntity> =
        AsyncListDiffer(this, WEATHER_DIFF_CALLBACK)

    fun setWeatherList(weathers: MutableList<WeatherEntity>) {
        if (weathers.isNotEmpty()) {
            weatherList = weathers
            differ.submitList(weathers)
        }
    }
}