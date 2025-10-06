package com.example.currencyconverter.ui.screen.transaction

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.data.local.entity.TransactionEntity
import com.example.currencyconverter.util.formatCurrency
import com.example.currencyconverter.util.formatDate
import com.example.currencyconverter.util.formatTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailScreen(
    transactionId: String,
    onBackClick: () -> Unit,
    viewModel: TransactionDetailViewModel = hiltViewModel()
) {
    val transaction by viewModel.transaction.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(transactionId) {
        viewModel.loadTransaction(transactionId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transaction Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                transaction != null -> TransactionDetailContent(transaction = transaction!!)
                else -> Text("Transaction not found")
            }
        }
    }
}

@Composable
private fun TransactionDetailContent(
    transaction: TransactionEntity,
    modifier: Modifier = Modifier
) {
    // Ensure case-insensitive type check and null safety
    val isDeposit = transaction.type.equals("DEPOSIT")
    val amountColor = if (isDeposit) Color(0xFF4CAF50) else Color(0xFFF44336)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Transaction Amount Card
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isDeposit) "Amount Deposited" else "Amount Withdrawn",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${if (isDeposit) "+" else "-"} ${formatCurrency(transaction.amount, transaction.currency)}",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = amountColor
                )

                if (transaction.currency != "INR") {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "≈ ${formatCurrency(transaction.amountInInr, "INR")}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }

        // Transaction Details Card
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DetailRow("Transaction ID", transaction.id.toString())
                DetailRow("Date", formatDate(transaction.timestamp))
                DetailRow("Time", formatTime(transaction.timestamp))
                DetailRow("Type", if (isDeposit) "Deposit" else "Withdrawal")

                if (!transaction.description.isNullOrBlank()) {
                    DetailRow("Description", transaction.description)
                }

                if (transaction.currency != "INR" && transaction.amount > 0) {
                    DetailRow(
                        "Exchange Rate",
                        "1 ${transaction.currency} = ${
                            String.format("%.4f", transaction.amountInInr / transaction.amount)
                        } INR"
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1.2f),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
