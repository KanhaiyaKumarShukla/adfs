package com.example.currencyconverter.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.entity.TransactionEntity
import com.example.currencyconverter.data.repository.CurrencyRepository
import com.example.currencyconverter.data.repository.TransactionRepository
import com.example.currencyconverter.result.NetworkResult
import com.example.currencyconverter.ui.states.DepositState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DepositViewModel @Inject constructor(
    private val currRepo: CurrencyRepository,
    private val tranRepo: TransactionRepository
) : ViewModel(){

    private val _ui = MutableStateFlow(DepositState())
    val ui:StateFlow<DepositState> = _ui.asStateFlow()

    fun loadRate(base:String, target:String){
        viewModelScope.launch{
            _ui.update{
                it.copy(loading = true, error = null)
            }
            when(val res= currRepo.getRates(base, target)){
                is NetworkResult.Success -> _ui.update{
                    it.copy(loading=false, lastConversion = res.data)
                }
                is NetworkResult.Failure -> _ui.update {
                    it.copy(
                        loading = false,
                        error = "Rate Failed"
                    )
                }
            }
        }
    }


    fun deposit(amount:Double, currency:String){
        viewModelScope.launch{
            _ui.update{
                it.copy(loading = true, error = null)
            }
            when(val res= currRepo.convertAmount(currency, "INR", amount)){
                is NetworkResult.Success -> {
                    val conv=res.data
                    val amountInInr = conv.conversion_result ?: (amount * conv.conversion_rate)

                    val tran = TransactionEntity(
                        type = "CR",
                        originalAmount = amount,
                        originalCurrency = currency,
                        amountInInr = amountInInr,
                        Timestamp = System.currentTimeMillis(),
                        status = "COMPLETED"
                    )
                    val id=tranRepo.insertTransaction(tran)
                    tranRepo.addToBalance(amountInInr)
                    _ui.update {
                        it.copy(loading=false, lastId = id, lastConversion = conv)
                    }
                }
                is NetworkResult.Failure -> {
                    _ui.update {
                        it.copy(
                            loading = false,
                            error = "Conversion Failed"
                        )
                    }
                }
            }
        }
    }

}