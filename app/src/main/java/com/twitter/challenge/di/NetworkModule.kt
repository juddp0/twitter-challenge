package com.twitter.challenge.di

import com.twitter.challenge.api.WeatherAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// TODO August 26, 2021 Also replace this with a gradle buildconfigfield
private const val URL = "https://twitter-code-challenge.s3.amazonaws.com/"

// TODO August 26, 2021 Use a real dependency injection framework if you have more time
object NetworkModule {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val weatherAPI: WeatherAPI by lazy { retrofit.create(WeatherAPI::class.java) }
}