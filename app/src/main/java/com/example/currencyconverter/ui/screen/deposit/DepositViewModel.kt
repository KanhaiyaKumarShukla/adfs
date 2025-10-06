package com.example.currencyconverter.ui.screen.deposit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.local.entity.TransactionEntity
import com.example.currencyconverter.domain.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DepositViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount

    private val _selectedCurrency = MutableStateFlow("USD")
    val selectedCurrency: StateFlow<String> = _selectedCurrency

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val currencies = listOf("USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "INR")
    
    private val _isCurrencyMenuExpanded = MutableStateFlow(false)
    val isCurrencyMenuExpanded: StateFlow<Boolean> = _isCurrencyMenuExpanded

    fun onAmountChange(newAmount: String) {
        if (newAmount.isEmpty() || newAmount.matches(Regex("^\\d*\\.?\\d*"))) {
            _amount.value = newAmount
        }
    }

    fun onCurrencySelected(currency: String) {
        _selectedCurrency.value = currency
    }

    fun onCurrencyMenuExpandedChange(expanded: Boolean) {
        _isCurrencyMenuExpanded.value = expanded
    }

    fun onDepositClick(onSuccess: () -> Unit) {
        val amountValue = _amount.value.toDoubleOrNull() ?: return
        if (amountValue <= 0) return
        val currency = _selectedCurrency.value
        
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Convert to INR if needed
                val amountInInr = if (currency == "INR") {
                    amountValue
                } else {
                    // Call API to get exchange rate
                    val rate = repository.getExchangeRate(currency, "INR")
                    amountValue * rate
                }
                
                // Create transaction entity with current timestamp
                val transaction = TransactionEntity(
                    type = TransactionEntity.TransactionType.DEPOSIT,
                    amount = amountValue,
                    currency = currency,
                    amountInInr = amountInInr,
                    description = "Deposit of $amountValue $currency",
                    timestamp = System.currentTimeMillis()
                )
                
                // Save transaction
                repository.insertTransaction(transaction)
                
                // Reset form
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
