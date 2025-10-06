package com.example.currencyconverter.ui.screen.deposit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositScreen(
    onBackClick: () -> Unit,
    onDepositSuccess: () -> Unit,
    viewModel: DepositViewModel = hiltViewModel()
) {
    val amount by viewModel.amount.collectAsState()
    val selectedCurrency by viewModel.selectedCurrency.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    // Use local state for menu expansion to ensure smooth UI updates
    var isCurrencyMenuExpanded by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Deposit") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Amount Input
            OutlinedTextField(
                value = amount,
                onValueChange = { viewModel.onAmountChange(it) },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Text(
                        text = "$",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                },
                singleLine = true
            )
            
            // Currency Selection
            ExposedDropdownMenuBox(
                expanded = isCurrencyMenuExpanded,
                onExpandedChange = { isExpanded ->
                    isCurrencyMenuExpanded = isExpanded
                    viewModel.onCurrencyMenuExpandedChange(isExpanded)
                }
            ) {
                OutlinedTextField(
                    value = selectedCurrency,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Currency") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCurrencyMenuExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                
                ExposedDropdownMenu(
                    expanded = isCurrencyMenuExpanded,
                    onDismissRequest = { 
                        isCurrencyMenuExpanded = false
                        viewModel.onCurrencyMenuExpandedChange(false) 
                    }
                ) {
                    viewModel.currencies.forEach { currency ->
                        DropdownMenuItem(
                            text = { Text(currency) },
                            onClick = {
                                viewModel.onCurrencySelected(currency)
                                isCurrencyMenuExpanded = false
                                viewModel.onCurrencyMenuExpandedChange(false)
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Deposit Button
            Button(
                onClick = {
                    viewModel.onDepositClick {
                        onDepositSuccess()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = amount.isNotBlank() && amount.toDoubleOrNull() != null && amount.toDouble() > 0
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text("Deposit")
                }
            }
        }
    }
}
