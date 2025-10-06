package com.example.currencyconverter.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.currencyconverter.ui.viewmodel.DepositViewModel

@Composable
fun DepositScreen (vm: DepositViewModel){
    val ui by vm.ui.collectAsState()
    var amtText by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf("USD") }
    var supported = listOf("USD", "EUR")
    LaunchedEffect(Unit) {
        vm.loadRate(currency, "INR")
    }

}