package com.example.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.example.currencyconverter.data.dao.BalanceDao
import com.example.currencyconverter.data.dao.TransactionDao
import com.example.currencyconverter.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "bank_db")
            .build()

    @Provides
    fun providerTransactionDao(db: AppDatabase): TransactionDao=db.transactionDao()

    @Provides
    fun provideBalanceDao(db: AppDatabase): BalanceDao = db.balanceDao()
}