package com.twitter.challenge.data

import com.google.gson.annotations.SerializedName

data class Rain(
        @SerializedName("3h")
        val threeHour: Int
)