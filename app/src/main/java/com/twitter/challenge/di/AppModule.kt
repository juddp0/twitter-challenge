package com.twitter.challenge.di

import com.twitter.challenge.repository.WeatherRepository
import com.twitter.challenge.repository.WeatherRepositoryContract

// TODO August 26, 2021 Actually implement Dependency Injection when you have more time
object AppModule {

    val weatherRepository: WeatherRepositoryContract by lazy {
        WeatherRepository(NetworkModule.weatherAPI)
    }
}