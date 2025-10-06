package com.example.currencyconverter.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity (
    @PrimaryKey(autoGenerate = true) val id:Long=0,
    val type:String,
    val originalAmount:Double,
    val originalCurrency:String,
    val amountInInr:Double,
    val Timestamp:Long,
    val status:String
)