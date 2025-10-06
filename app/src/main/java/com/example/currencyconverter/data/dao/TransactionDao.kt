package com.example.currencyconverter.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.example.currencyconverter.data.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    //insert
    @Insert
    suspend fun insert(tran: TransactionEntity): Long

    @Update
    suspend fun update(tran: TransactionEntity)



    //get transaction

    fun getAllTrans(): Flow<List<TransactionEntity>>

    // pending tran

    fun getPendingTrans(): Flow<List<TransactionEntity>>

}