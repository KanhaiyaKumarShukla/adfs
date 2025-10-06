package com.example.currencyconverter.ui.states

data class WithdrawUiState (
    val loading : Boolean = false,
    val error : String? = null,
    val lastId:Long ?= null
)
