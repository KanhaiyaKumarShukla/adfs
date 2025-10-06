package com.example.currencyconverter.data.local.dao

import androidx.room.*
import com.example.currencyconverter.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: Long): TransactionEntity?
    
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentTransactions(limit: Int): Flow<List<TransactionEntity>>
    
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>
    
    @Query("SELECT * FROM transactions WHERE type = 'DEPOSIT' ORDER BY timestamp DESC")
    fun getDeposits(): Flow<List<TransactionEntity>>
    
    @Query("SELECT * FROM transactions WHERE type = 'WITHDRAWAL' ORDER BY timestamp DESC")
    fun getWithdrawals(): Flow<List<TransactionEntity>>
    
    @Query("SELECT SUM(amountInInr) FROM transactions WHERE type = 'DEPOSIT'")
    fun getTotalDeposits(): Flow<Double?>
    
    @Query("SELECT SUM(amountInInr) FROM transactions WHERE type = 'WITHDRAWAL'")
    fun getTotalWithdrawals(): Flow<Double?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long
    
    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)
    
    @Query("DELETE FROM transactions")
    suspend fun deleteAllTransactions()
}
