package com.example.currencyconverter.ui.states

import com.example.currencyconverter.data.local.ConversionResponse

data class DepositState (
    val loading :Boolean = false,
    val error:String ? = null,
    val lastId:Long ?=null,
    val lastConversion: ConversionResponse?=null

)
