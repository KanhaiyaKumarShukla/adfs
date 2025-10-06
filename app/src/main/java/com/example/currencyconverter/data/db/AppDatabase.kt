package com.example.currencyconverter.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyconverter.data.dao.BalanceDao
import com.example.currencyconverter.data.dao.TransactionDao
import com.example.currencyconverter.data.entity.BalanceEntity
import com.example.currencyconverter.data.entity.TransactionEntity

@Database(entities=[TransactionEntity::class, BalanceEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun balanceDao(): BalanceDao
}