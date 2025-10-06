package com.example.currencyconverter.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "balance")
data class BalanceEntity (
    @PrimaryKey val id:Int=0,
    val totalInInr:Double,
)