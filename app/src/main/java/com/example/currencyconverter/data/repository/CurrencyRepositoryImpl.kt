package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.local.dao.TransactionDao
import com.example.currencyconverter.data.local.entity.TransactionEntity
import com.example.currencyconverter.data.remote.api.ExchangeRateApi
import com.example.currencyconverter.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepositoryImpl @Inject constructor(
    private val api: ExchangeRateApi,
    private val transactionDao: TransactionDao,
    private val appPreferences: AppPreferences
) : CurrencyRepository {

    override suspend fun getExchangeRate(fromCurrency: String, toCurrency: String): Double {
        val response = api.getExchangeRate(fromCurrency, toCurrency)
        if (response.isSuccessful && response.body()?.result == "success") {
            return response.body()?.conversionRate ?: 0.0
        }
        throw Exception("Failed to get exchange rate: ${response.errorBody()?.string()}")
    }

    override suspend fun convertCurrency(fromCurrency: String, toCurrency: String, amount: Double): Double {
        val response = api.convertCurrency(fromCurrency, toCurrency, amount)
        if (response.isSuccessful && response.body()?.result == "success") {
            return response.body()?.conversionResult ?: 0.0
        }
        throw Exception("Failed to convert currency: ${response.errorBody()?.string()}")
    }

    override suspend fun insertTransaction(transaction: TransactionEntity): Long {
        return transactionDao.insertTransaction(transaction)
    }

    override fun getAllTransactions(): Flow<List<TransactionEntity>> {
        return transactionDao.getAllTransactions()
    }

    override fun getDeposits(): Flow<List<TransactionEntity>> {
        return transactionDao.getDeposits()
    }

    override fun getWithdrawals(): Flow<List<TransactionEntity>> {
        return transactionDao.getWithdrawals()
    }

    override fun getTotalDeposits(): Flow<Double?> {
        return transactionDao.getTotalDeposits()
    }

    override fun getTotalWithdrawals(): Flow<Double?> {
        return transactionDao.getTotalWithdrawals()
    }

    override suspend fun deleteAllTransactions() {
        transactionDao.deleteAllTransactions()
    }

    override suspend fun isFirstLaunch(): Boolean {
        return appPreferences.isFirstLaunch()
    }

    override suspend fun setFirstLaunch(isFirstLaunch: Boolean) {
        appPreferences.setFirstLaunch(isFirstLaunch)
    }
}
