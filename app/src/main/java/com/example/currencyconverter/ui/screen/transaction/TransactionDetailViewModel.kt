package com.example.currencyconverter.ui.screen.transaction

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
class TransactionDetailViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    private val _transaction = MutableStateFlow<TransactionEntity?>(null)
    val transaction: StateFlow<TransactionEntity?> = _transaction

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadTransaction(transactionId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val transaction = repository.getTransactionById(transactionId.toLong())
                if (transaction != null) {
                    _transaction.value = transaction
                } else {
                    // Handle case where transaction is not found
                    _transaction.value = null
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
                _transaction.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
}
