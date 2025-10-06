package com.example.currencyconverter.ui.states

import com.example.currencyconverter.data.entity.TransactionEntity

data class HomeUiState (
    val balanceInInr: Double =0.0,
    val transactions: List<TransactionEntity> = emptyList(),
    val pending : List<TransactionEntity> = emptyList()
)