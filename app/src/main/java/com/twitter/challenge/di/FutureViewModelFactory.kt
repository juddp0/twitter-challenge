package com.twitter.challenge.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.twitter.challenge.repository.WeatherRepositoryContract
import com.twitter.challenge.ui.future.FutureForecastViewModel

object FutureViewModelFactory : ViewModelProvider.Factory {

    private val repo: WeatherRepositoryContract by lazy {
        AppModule.weatherRepository
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FutureForecastViewModel::class.java)) {
            return FutureForecastViewModel(repo) as T
        }
        throw IllegalAccessException("Please use the MainViewModel")
    }

}