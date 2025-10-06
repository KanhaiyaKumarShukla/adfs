package com.example.currencyconverter.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: TransactionType,
    val amount: Double,
    val currency: String,
    val amountInInr: Double,
    val timestamp: Long = System.currentTimeMillis(),
    val description: String = ""
) {
    enum class TransactionType {
        DEPOSIT, WITHDRAWAL
    }
}
