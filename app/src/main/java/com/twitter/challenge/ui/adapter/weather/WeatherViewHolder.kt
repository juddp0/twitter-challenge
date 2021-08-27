package com.twitter.challenge.ui.adapter.weather

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.twitter.challenge.R
import com.twitter.challenge.data.Forecast
import com.twitter.challenge.utils.TemperatureConverter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class WeatherViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val formatter: DateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    private val weatherImageView: AppCompatImageView by lazy {
        itemView.findViewById(R.id.weatherImageView)
    }
    private val weatherTitle: TextView by lazy {
        itemView.findViewById(R.id.weatherTitle)
    }
    private val weatherTemp: TextView by lazy {
        itemView.findViewById(R.id.weatherTemperature)
    }
    private val context: Context by lazy { itemView.context }

    fun bindDataToView(forecast: Forecast) {
        setWeatherImageDrawable(forecast)
        weatherTitle.text = getDayOfWeek(forecast)
        weatherTemp.text = context.getString(R.string.temperature,
                forecast.weatherResponse.weather.temperatureInCelsius,
                TemperatureConverter.celsiusToFahrenheit(forecast.weatherResponse.weather.temperatureInCelsius))
    }

    private fun setWeatherImageDrawable(forecast: Forecast) {
        when {
            forecast.weatherResponse.clouds.cloudiness > 50 -> ContextCompat.getDrawable(context, R.drawable.ic_cloudy)
            else -> ContextCompat.getDrawable(context, R.drawable.ic_sunny)
        }?.let {
            weatherImageView.setImageDrawable(it)
        }
    }

    private fun getDayOfWeek(forecast: Forecast): String {
        val calendar = Calendar.getInstance().apply {
            time = Date()
            add(Calendar.DATE, forecast.day)
        }
        return formatter.format(calendar.time)
    }

}