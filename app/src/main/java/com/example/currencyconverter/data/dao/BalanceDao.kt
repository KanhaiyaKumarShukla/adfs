package com.example.currencyconverter.data.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconverter.data.entity.BalanceEntity
import kotlinx.coroutines.flow.Flow

interface BalanceDao {

    // getbalance
    @Query("SELECT * FROM balance LIMIT 1")
    fun getBalanceFlow(): Flow<BalanceEntity?>

    // get onece
    @Query("SELECT * FROM balance LIMIT 1")
    suspend fun getBalanceOnce(): BalanceEntity?

    // upsert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(balance: BalanceEntity)
}