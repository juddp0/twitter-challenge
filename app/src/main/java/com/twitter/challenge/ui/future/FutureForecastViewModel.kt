package com.twitter.challenge.ui.future

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.twitter.challenge.data.Forecast
import com.twitter.challenge.data.NetworkResponse
import com.twitter.challenge.repository.WeatherRepositoryContract
import com.twitter.challenge.ui.main.WeatherFetchException
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.sqrt

class FutureForecastViewModel(
    private val weatherRepo: WeatherRepositoryContract
) : ViewModel() {
    private val SQUARE_BY_TWO = 2.0

    val weatherData = MutableLiveData<NetworkResponse<List<Forecast>>>()
    val standardDeviation: LiveData<NetworkResponse<Double>> =
        Transformations.map(weatherData) { transformToStandardDeviation(it) }

    fun fetchWeather() {
        viewModelScope.launch {
            try {
                weatherData.postValue(NetworkResponse.Success(weatherRepo.fetchNextFiveDays()))
            } catch (e: Throwable) {
                weatherData.postValue(NetworkResponse.Error(WeatherFetchException()))
            }
        }
    }

    private fun transformToStandardDeviation(weatherData: NetworkResponse<List<Forecast>>): NetworkResponse<Double> =
        when (weatherData) {
            is NetworkResponse.Loading -> NetworkResponse.Loading()
            is NetworkResponse.Error -> NetworkResponse.Error(WeatherFetchException())
            is NetworkResponse.Success -> NetworkResponse.Success(calculateStandardDeviation(weatherData.data))
        }

    @VisibleForTesting fun calculateStandardDeviation(forecasts: List<Forecast>): Double {
        return sqrt(calculateVariance(forecasts))
    }

    @VisibleForTesting fun calculateVariance(forecasts: List<Forecast>): Double {
        val mean = calculateMean(forecasts)
        var sumOfNumerator: Double = 0.0
        forecasts.map {
            val minus = (it.weatherResponse.weather.temperatureInCelsius - mean)
            val powered = minus.pow(SQUARE_BY_TWO)
            sumOfNumerator += powered
        }
        return sumOfNumerator / (forecasts.size - 1)
    }

    @VisibleForTesting fun calculateMean(forecasts: List<Forecast>) = forecasts.map { it.weatherResponse.weather.temperatureInCelsius }.average()
}