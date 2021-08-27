package com.twitter.challenge.repository

import com.twitter.challenge.api.WeatherAPI
import com.twitter.challenge.data.WeatherResponse
import kotlinx.coroutines.*

interface WeatherRepositoryContract {

    suspend fun fetchCurrentWeather(): WeatherResponse

    suspend fun fetchNextFiveDays(): List<WeatherResponse>
}

class WeatherRepository(
        private val api: WeatherAPI,
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepositoryContract {

    override suspend fun fetchCurrentWeather() = api.fetchCurrentWeather()

    override suspend fun fetchNextFiveDays(): List<WeatherResponse> {
        return withContext(dispatcher) {
            val deferredList = mutableListOf<Deferred<WeatherResponse>>()
            for (i in 1..5) {
                deferredList.add(async { api.fetchFutureWeather(i) })
            }
            return@withContext deferredList.awaitAll()
        }
    }

}
