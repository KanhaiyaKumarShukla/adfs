package com.example.currencyconverter.data.repository

import android.view.SurfaceControl
import com.example.currencyconverter.data.dao.BalanceDao
import com.example.currencyconverter.data.dao.TransactionDao
import com.example.currencyconverter.data.entity.BalanceEntity
import com.example.currencyconverter.data.entity.TransactionEntity
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class TransactionRepository @Inject constructor(
    private val balanceDao: BalanceDao,
    private val tranDao: TransactionDao
){
    fun observeTransactions():Flow<List<TransactionEntity>> = tranDao.getAllTrans()

     suspend fun insertTransaction(trans: TransactionEntity): Long = withContext(Dispatchers.IO){
        tranDao.insert(trans)
    }

    suspend fun updateTransaction(trans: TransactionEntity) = withContext(Dispatchers.IO){
        tranDao.update(trans)
    }

    fun observePending():Flow<List<TransactionEntity>> = tranDao.getPendingTrans()


    suspend fun setBalanceDirectly(totalInInr:Double)= withContext(Dispatchers.IO){
        balanceDao.upsert(BalanceEntity(id=0, totalInInr=totalInInr))
    }
    suspend fun addToBalance(delInInr: Double) = withContext(Dispatchers.IO){
        val current = balanceDao.getBalanceOnce()?.totalInInr ?: 0.0
        balanceDao.upsert(BalanceEntity(id=0, totalInInr = current+delInInr))
    }

    suspend fun subtractFromBalance(delInInr: Double) = withContext(Dispatchers.IO){
        val current = balanceDao.getBalanceOnce()?.totalInInr ?: 0.0
        balanceDao.upsert(BalanceEntity(id=0, totalInInr = current-delInInr))
    }

    suspend fun getBalanceOnce():Double = withContext(Dispatchers.IO){
        balanceDao.getBalanceOnce()?.totalInInr ?:0.0
    }

    fun observeBalance():Flow<BalanceEntity?> = balanceDao.getBalanceFlow()


}