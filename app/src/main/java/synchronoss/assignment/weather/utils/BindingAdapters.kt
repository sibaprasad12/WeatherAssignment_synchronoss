package synchronoss.assignment.weather.utils

import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso
import synchronoss.assignment.weather.R
import synchronoss.assignment.weather.models.WeatherEntity
import synchronoss.assignment.weather.ui.adapter.WeatherAdapter

/**
 * Created by Sibaprasad Mohanty on 12/11/21.
 * sibaprasad.x.mohanty@gmail.com
 */

object BindingAdapters {

    @JvmStatic
    @BindingAdapter(value = ["setAdapter"])
    fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
        this.run {
            this.setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["updateWeatherList"])
    fun RecyclerView.updateRecyclerViewAdapter(
        weatherList: MutableList<WeatherEntity>?
    ) {
        val adapter = this.adapter as WeatherAdapter
        if (weatherList != null) {
            adapter.setWeatherList(weatherList)
        }
    }

    @JvmStatic
    @BindingAdapter("weatherImage")
    fun loadImage(imageview: AppCompatImageView, imageUrl: String?) {
        val fullImageUrl = "https://openweathermap.org/img/w/" + imageUrl + ".png"
        Log.i("TAG", fullImageUrl)
        Picasso.get()
            .load(fullImageUrl)
            .error(R.mipmap.ic_launcher_round)
            .into(imageview)
    }

    @JvmStatic
    @BindingAdapter("show")
    fun showOrHideProgress(view: View, showHideStatus: Boolean) {
        view.visibility = if (showHideStatus) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("errorVisibleGone")
    fun showOrHideError(view: View, error: String) {
        view.visibility = if (error.isEmpty()) View.GONE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("errorMessage")
    fun showErrorMessage(view: AppCompatTextView, message: String) {
        message.let {
            view.text = message
        }
    }

    @set:BindingAdapter("invisible")
    var View.invisible
        get() = visibility == View.INVISIBLE
        set(value) {
            visibility = if (value) View.INVISIBLE else View.VISIBLE
        }

    @set:BindingAdapter("gone")
    var View.gone
        get() = visibility == View.GONE
        set(value) {
            visibility = if (value) View.GONE else View.VISIBLE
        }


    @JvmStatic
    @BindingAdapter("weatherDescription")
    fun showWeatherDescription(view: AppCompatTextView, message: String) {
        message.let {
            view.text = message
        }
    }

    @JvmStatic
    @BindingAdapter("cardBackground")
    fun setCardBackground(cardview: CardView, weather: String) {
        when (weather) {
            "Rain" -> {
                cardview.setBackgroundColor(
                    ContextCompat.getColor(
                        cardview.context,
                        R.color.white
                    )
                )
            }
            "Clouds" -> {
                cardview.setBackgroundColor(
                    ContextCompat.getColor(
                        cardview.context,
                        R.color.card_cloudy
                    )
                )
            }
            else -> {
                cardview.setBackgroundColor(
                    ContextCompat.getColor(
                        cardview.context,
                        R.color.card_sunny
                    )
                )
            }
        }
    }

    @JvmStatic
    @BindingAdapter("textChangedListener")
    fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher) {
        editText.addTextChangedListener(textWatcher)
    }

    @JvmStatic
    @BindingAdapter("onRefreshListener")
    fun setSwipeRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout, isRefreshing: Boolean) {
        swipeRefreshLayout.isRefreshing = isRefreshing
    }

}