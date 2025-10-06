package com.example.currencyconverter.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.local.entity.TransactionEntity
import com.example.currencyconverter.domain.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    private val _balance = MutableStateFlow(0.0)
    val balance: StateFlow<Double> = _balance

    private val _recentTransactions = MutableStateFlow<List<TransactionEntity>>(emptyList())
    val recentTransactions: StateFlow<List<TransactionEntity>> = _recentTransactions

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadInitialData()
    }

    fun loadInitialData() {
        viewModelScope.launch {
            _isLoading.value = true
            
            try {
                // Load balance
                repository.getTotalDeposits().collectLatest { deposits ->
                    repository.getTotalWithdrawals().collectLatest { withdrawals ->
                        _balance.value = (deposits ?: 0.0) - (withdrawals ?: 0.0)
                    }
                }

                // Load recent transactions
                repository.getAllTransactions().collectLatest { transactions ->
                    _recentTransactions.value = transactions.take(5) // Show only 5 most recent
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshData() {
        loadInitialData()
    }
}
