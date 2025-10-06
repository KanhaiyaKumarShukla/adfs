package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getExchangeRate(fromCurrency: String, toCurrency: String): Double
    suspend fun convertCurrency(fromCurrency: String, toCurrency: String, amount: Double): Double
    
    // Transaction operations
    suspend fun insertTransaction(transaction: TransactionEntity): Long
    fun getAllTransactions(): Flow<List<TransactionEntity>>
    fun getDeposits(): Flow<List<TransactionEntity>>
    fun getWithdrawals(): Flow<List<TransactionEntity>>
    fun getTotalDeposits(): Flow<Double?>
    fun getTotalWithdrawals(): Flow<Double?>
    suspend fun deleteAllTransactions()
    
    // App preferences
    suspend fun isFirstLaunch(): Boolean
    suspend fun setFirstLaunch(isFirstLaunch: Boolean)
}
