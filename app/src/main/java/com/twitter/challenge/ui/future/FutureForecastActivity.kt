package com.twitter.challenge.ui.future

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.twitter.challenge.R
import com.twitter.challenge.data.NetworkResponse
import com.twitter.challenge.di.FutureViewModelFactory
import com.twitter.challenge.ui.adapter.weather.WeatherAdapter

class FutureForecastActivity : AppCompatActivity() {

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.futureRecyclerView)
    }
    private val progressBar: ProgressBar by lazy {
        findViewById(R.id.futureProgressBar)
    }
    private val standardDeviationProgressBar: ProgressBar by lazy {
        findViewById(R.id.standardDeviationProgressBar)
    }
    private val standardDeviationTitle: TextView by lazy {
        findViewById(R.id.standardDeviationTitle)
    }
    private val weatherAdapter: WeatherAdapter by lazy { WeatherAdapter() }
    private val viewModel: FutureForecastViewModel by lazy {
        ViewModelProvider(this, FutureViewModelFactory).get(FutureForecastViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_future_forecast)
        observeWeather()
        observeStandardDeviation()
        recyclerView.adapter = weatherAdapter
        viewModel.fetchWeather()
    }

    private fun observeWeather() {
        viewModel.weatherData.observe(this, {
            when (it) {
                is NetworkResponse.Success -> {
                    progressBar.visibility = View.GONE
                    weatherAdapter.addWeather(it.data)
                }
                is NetworkResponse.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, R.string.weather_fetch_error, Toast.LENGTH_LONG).show()
                }
                is NetworkResponse.Loading -> progressBar.visibility = View.VISIBLE
            }
        })
    }

    private fun observeStandardDeviation() {
        viewModel.standardDeviation.observe(this, {
            when(it) {
                is NetworkResponse.Loading -> standardDeviationProgressBar.visibility = View.VISIBLE
                is NetworkResponse.Success -> {
                    standardDeviationProgressBar.visibility = View.GONE
                    standardDeviationTitle.text = getString(R.string.future_standard_deviation, it.data)
                }
                is NetworkResponse.Error -> {
                    standardDeviationProgressBar.visibility = View.GONE
                }
            }
        })
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, FutureForecastActivity::class.java)
    }
}