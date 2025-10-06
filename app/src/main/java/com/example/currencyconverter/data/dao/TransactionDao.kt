package com.example.currencyconverter.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
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
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTrans(): Flow<List<TransactionEntity>>

    // pending tran
    @Query("SELECT * FROM transactions WHERE status = 'PENDING' ORDER BY timestamp ASC")
    fun getPendingTrans(): Flow<List<TransactionEntity>>

}