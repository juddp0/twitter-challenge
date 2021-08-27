package com.twitter.challenge.ui.adapter.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.twitter.challenge.R
import com.twitter.challenge.data.Forecast

class WeatherAdapter : RecyclerView.Adapter<WeatherViewHolder>() {

    private val forecasts = mutableListOf<Forecast>()

    fun addWeather(forecasts: List<Forecast>) {
        this.forecasts.addAll(forecasts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            WeatherViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_item_weather, parent, false))

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bindDataToView(forecasts[position])
    }

    override fun getItemCount() = forecasts.size

}