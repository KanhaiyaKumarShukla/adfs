package com.example.currencyconverter.data.remote.api

import com.example.currencyconverter.data.remote.response.ExchangeRateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRateApi {
    @GET("v6/8cea16c76b5c2669eae114d1/pair/{fromCurrency}/{toCurrency}")
    suspend fun getExchangeRate(
        @Path("fromCurrency") fromCurrency: String,
        @Path("toCurrency") toCurrency: String
    ): Response<ExchangeRateResponse>
    
    @GET("v6/8cea16c76b5c2669eae114d1/pair/{fromCurrency}/{toCurrency}/{amount}")
    suspend fun convertCurrency(
        @Path("fromCurrency") fromCurrency: String,
        @Path("toCurrency") toCurrency: String,
        @Path("amount") amount: Double
    ): Response<ExchangeRateResponse>
}
