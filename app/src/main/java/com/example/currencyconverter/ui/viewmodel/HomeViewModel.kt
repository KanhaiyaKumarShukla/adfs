package com.example.currencyconverter.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.data.repository.TransactionRepository
import com.example.currencyconverter.ui.states.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: TransactionRepository): ViewModel(){
    private val _ui = MutableStateFlow(HomeUiState())
    val ui:StateFlow<HomeUiState> = _ui.asStateFlow()

    init{
        viewModelScope.launch{
            combine(
                repo.observeBalance().map{it?.totalInInr ?:0.0},
                repo.observeTransactions(),
                repo.observePending()
            ){ b, t, p ->
                HomeUiState(balanceInInr = b, transactions = t, pending = p)
            }.collect{
                _ui.value = it
            }
        }
    }


}