package com.example.currencyconverter.data.remote

import com.example.currencyconverter.data.local.ConversionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{apiKey}/pair/{base}/{target}")
    suspend fun getRate(
        @Path("apiKey") apiKey:String,
        @Path("base") base:String,
        @Path("target") target:String
    ): Response<ConversionResponse>

    @GET("{apiKey}/pair/{base}/{target}/{amount}")
    suspend fun convertAmount(
        @Path("apiKey") apiKey:String,
        @Path("base") base:String,
        @Path("target")target:String,
        @Path("amount")amount:Double
    ): Response<ConversionResponse>


}