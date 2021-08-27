package com.twitter.challenge.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
        @SerializedName("coord")
        val coordinates: Coordinates,
        val weather: Weather,
        val wind: Wind,
        val rain: Rain,
        val clouds: Clouds,
        val name: String
)