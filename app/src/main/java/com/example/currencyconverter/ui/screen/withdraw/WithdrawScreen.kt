package com.example.currencyconverter.ui.screen.withdraw

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
fun WithdrawScreen(
    onBackClick: () -> Unit,
    onWithdrawSuccess: () -> Unit,
    viewModel: WithdrawViewModel = hiltViewModel()
) {
    val amount by viewModel.amount.collectAsState()
    val balance by viewModel.balance.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Withdraw") },
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
            // Available Balance
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Available Balance",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "₹${String.format("%.2f", balance)}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            // Amount Input
            OutlinedTextField(
                value = amount,
                onValueChange = { viewModel.onAmountChange(it) },
                label = { Text("Amount to Withdraw") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Text(
                        text = "₹",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                },
                singleLine = true,
                isError = amount.isNotBlank() && (amount.toDoubleOrNull() ?: 0.0) > balance
            )
            
            if (amount.isNotBlank() && (amount.toDoubleOrNull() ?: 0.0) > balance) {
                Text(
                    text = "Insufficient balance",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Withdraw Button
            Button(
                onClick = {
                    viewModel.onWithdrawClick {
                        onWithdrawSuccess()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = amount.isNotBlank() && 
                         amount.toDoubleOrNull() != null && 
                         amount.toDouble() > 0 &&
                         amount.toDouble() <= balance
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text("Withdraw")
                }
            }
        }
    }
}
