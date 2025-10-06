package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.local.ConversionResponse
import com.example.currencyconverter.data.remote.ApiService
import com.example.currencyconverter.result.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import retrofit2.Response

class CurrencyRepository @Inject constructor(private val api: ApiService){
    private val apiKey="8cea16c76b5c2669eae114d1"

    suspend fun getRates(base:String, target:String): NetworkResult<ConversionResponse> {
        return withContext(Dispatchers.IO){
            val res : Response<ConversionResponse> = api.getRate(apiKey, base, target)

            if(res.isSuccessful){
                val body = res.body()
                if(body!=null) NetworkResult.Success(body)
                else NetworkResult.Failure(res.code(), "Empty body")
            }else{
                NetworkResult.Failure(res.code(), res.errorBody()?.string() ?: res.message())
            }
        }
    }

    suspend fun convertAmount(base:String, target:String, amount:Double): NetworkResult<ConversionResponse> {
        return withContext(Dispatchers.IO){
            val res : Response<ConversionResponse> = api.convertAmount(apiKey, base, target, amount)

            if(res.isSuccessful){
                val body = res.body()
                if(body!=null) NetworkResult.Success(body)
                else NetworkResult.Failure(res.code(), "Empty body")
            }else{
                NetworkResult.Failure(res.code(), res.errorBody()?.string() ?: res.message())
            }
        }
    }

}