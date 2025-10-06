package com.example.currencyconverter.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.currencyconverter.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(vm: HomeViewModel) {
    val state by vm.ui.collectAsState()

}