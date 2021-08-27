package com.twitter.challenge.api

import com.twitter.challenge.data.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherAPI {

    @GET("current.json")
    suspend fun fetchCurrentWeather(): WeatherResponse

    @GET("future_{day}.json")
    suspend fun fetchFutureWeather(@Path("day") futureDayOffset: Int): WeatherResponse
}