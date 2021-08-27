package com.twitter.challenge.data

import com.google.gson.annotations.SerializedName

data class Weather(
        @SerializedName("temp")
        val temperatureInCelsius: Float,
        val pressure: Int,
        val humidity: Int
)