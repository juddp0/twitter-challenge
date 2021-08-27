package com.twitter.challenge.data

sealed class NetworkResponse<T> {
    data class Success<T>(val data: T): NetworkResponse<T>()
    data class Error<T>(val throwable: Throwable): NetworkResponse<T>()
    class Loading<T> : NetworkResponse<T>()
}