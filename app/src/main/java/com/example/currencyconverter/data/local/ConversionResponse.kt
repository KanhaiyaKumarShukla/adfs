package com.example.currencyconverter.data.local

data class ConversionResponse (
    val result : String,
    val base_code:String,
    val target_coe:String,
    val conversion_rate:Double,
    val conversion_result: Double?
)