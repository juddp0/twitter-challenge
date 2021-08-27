package com.twitter.challenge.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twitter.challenge.data.Forecast
import com.twitter.challenge.data.NetworkResponse
import com.twitter.challenge.repository.WeatherRepositoryContract
import kotlinx.coroutines.launch

class MainViewModel(
        private val weatherRepo: WeatherRepositoryContract
) : ViewModel() {

    val weatherData = MutableLiveData<NetworkResponse<Forecast>>()

    fun fetchWeather() {
        viewModelScope.launch {
            try {
                weatherData.postValue(NetworkResponse.Success(weatherRepo.fetchCurrentWeather()))
            } catch (e: Throwable) {
                weatherData.postValue(NetworkResponse.Error(WeatherFetchException()))
            }
        }
    }
}

class WeatherFetchException : Throwable()