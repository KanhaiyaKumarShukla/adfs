package com.example.currencyconverter.di

import com.example.currencyconverter.data.dao.BalanceDao
import com.example.currencyconverter.data.dao.TransactionDao
import com.example.currencyconverter.data.remote.ApiService
import com.example.currencyconverter.data.repository.CurrencyRepository
import com.example.currencyconverter.data.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCurrencyRepository(apiService: ApiService): CurrencyRepository = CurrencyRepository(apiService)

    @Provides
    @Singleton
    fun provideTransactionRepository(transactionDao: TransactionDao, balanceDao: BalanceDao): TransactionRepository =
        TransactionRepository(balanceDao, transactionDao)
}