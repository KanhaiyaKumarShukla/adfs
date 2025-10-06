package com.example.currencyconverter.result

sealed class NetworkResult<out T> {
    data class Success<T>(val data:T): NetworkResult<T>()
    data class Failure(val code:Int?, val message:String): NetworkResult<Nothing>()
}