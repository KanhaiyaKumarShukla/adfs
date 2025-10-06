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
import com.example.currencyconverter.data.entity.TransactionEntity
import com.example.currencyconverter.result.NetworkResult
import com.example.currencyconverter.ui.states.WithdrawUiState
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class WithdrawViewModel @Inject  constructor(private val transRepo: TransactionRepository) : ViewModel(){
    private val _ui = MutableStateFlow(WithdrawUiState())
    val ui = _ui.asStateFlow()

    fun withdraw(amountInInr:Double){
        viewModelScope.launch {
            _ui.update{
                it.copy(loading = true, error = null)
            }
            val res= transRepo.getBalanceOnce()
            if(amountInInr <=0.0){
                _ui.update {
                    it.copy(loading = false, error="Invalid")
                }
                return@launch
            }
            if(amountInInr>res){
                _ui.update {
                    it.copy(loading = false, error="Insufficient")
                }
                return@launch
            }
            val trans= TransactionEntity(
                type = "DR",
                originalAmount = amountInInr,
                originalCurrency = "INR",
                amountInInr = amountInInr,
                Timestamp = System.currentTimeMillis(),
                status = "COMPLETED"
            )
            val id=transRepo.insertTransaction(trans)
            transRepo.subtractFromBalance(amountInInr)
            _ui.update { it.copy(loading = false, lastId = id) }
        }
    }
}