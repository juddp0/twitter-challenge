package com.twitter.challenge.repository

import com.twitter.challenge.api.WeatherAPI
import com.twitter.challenge.data.Forecast
import kotlinx.coroutines.*

interface WeatherRepositoryContract {

    suspend fun fetchCurrentWeather(): Forecast

    suspend fun fetchNextFiveDays(): List<Forecast>
}

class WeatherRepository(
        private val api: WeatherAPI,
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepositoryContract {

    private val TODAY = 0

    override suspend fun fetchCurrentWeather() = Forecast(TODAY, api.fetchCurrentWeather())

    override suspend fun fetchNextFiveDays(): List<Forecast> {
        return withContext(dispatcher) {
            val deferredList = mutableListOf<Deferred<Forecast>>()
            for (i in 1..5) {
                deferredList.add(async { Forecast(i, api.fetchFutureWeather(i)) })
            }
            return@withContext deferredList.awaitAll()
        }
    }

}
