package com.twitter.challenge.ui.main

import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.twitter.challenge.R
import com.twitter.challenge.data.NetworkResponse
import com.twitter.challenge.data.NetworkResponse.Loading
import com.twitter.challenge.di.MainViewModelFactory
import com.twitter.challenge.ui.adapter.weather.WeatherAdapter
import com.twitter.challenge.ui.future.FutureForecastActivity


class MainActivity : AppCompatActivity() {

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.mainRecyclerView)
    }
    private val progressBar: ProgressBar by lazy {
        findViewById(R.id.mainProgressBar)
    }
    private val futureButton: AppCompatButton by lazy {
        findViewById(R.id.mainFutureButton)
    }
    private val weatherAdapter: WeatherAdapter by lazy { WeatherAdapter() }
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeWeather()
        recyclerView.adapter = weatherAdapter
        viewModel.fetchWeather()
        setFutureButton()
    }

    private fun setFutureButton() {
        futureButton.setOnClickListener {
            startActivity(FutureForecastActivity.createIntent(this))
        }
    }

    private fun observeWeather() {
        viewModel.weatherData.observe(this, {
            when(it) {
                is NetworkResponse.Success -> {
                    progressBar.visibility = View.GONE
                    weatherAdapter.addWeather(listOf(it.data))
                }
                is NetworkResponse.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, R.string.weather_fetch_error, Toast.LENGTH_LONG).show()
                }
                is Loading -> progressBar.visibility = View.VISIBLE
            }
        })
    }

}