package com.example.currencyconverter.ui.screen.withdraw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.local.entity.TransactionEntity
import com.example.currencyconverter.domain.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount

    private val _balance = MutableStateFlow(0.0)
    val balance: StateFlow<Double> = _balance

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadBalance()
    }

    private fun loadBalance() {
        viewModelScope.launch {
            combine(
                repository.getTotalDeposits(),
                repository.getTotalWithdrawals()
            ) { deposits, withdrawals ->
                (deposits ?: 0.0) - (withdrawals ?: 0.0)
            }.collect { currentBalance ->
                _balance.value = currentBalance
            }
        }
    }

    fun onAmountChange(newAmount: String) {
        if (newAmount.isEmpty() || newAmount.matches(Regex("^\\d*\\.?\\d*"))) {
            _amount.value = newAmount
        }
    }

    fun onWithdrawClick(onSuccess: () -> Unit) {
        val amountValue = _amount.value.toDoubleOrNull() ?: return
        if (amountValue <= 0) return
        
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Create transaction entity with current timestamp
                val transaction = TransactionEntity(
                    type = TransactionEntity.TransactionType.WITHDRAWAL,
                    amount = amountValue,
                    currency = "INR",
                    amountInInr = amountValue,
                    description = "Withdrawal of ₹$amountValue",
                    timestamp = System.currentTimeMillis()
                )
                
                // Save withdrawal transaction
                repository.insertTransaction(transaction)
                
                // Reset amount
                _amount.value = ""
                
                onSuccess()
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
