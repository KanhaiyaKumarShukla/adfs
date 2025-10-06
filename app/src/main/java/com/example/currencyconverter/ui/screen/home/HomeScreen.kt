package com.example.currencyconverter.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.data.local.entity.TransactionEntity
import com.example.currencyconverter.ui.components.TransactionItem
import com.example.currencyconverter.util.formatCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onDepositClick: () -> Unit,
    onWithdrawClick: () -> Unit,
    onTransactionClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val balance by viewModel.balance.collectAsState()
    val recentTransactions by viewModel.recentTransactions.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Currency Converter") },
                actions = {
                    IconButton(onClick = { /* Handle settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle new transaction */ },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Balance Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Available Balance",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = formatCurrency(balance),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Deposit Button
                            ActionButton(
                                text = "Deposit",
                                icon = Icons.Default.ArrowDownward,
                                onClick = onDepositClick,
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White
                            )
                            
                            // Withdraw Button
                            ActionButton(
                                text = "Withdraw",
                                icon = Icons.Default.ArrowUpward,
                                onClick = onWithdrawClick,
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = Color.White
                            )
                        }
                    }
                }
            }
            
            // Recent Transactions
            item {
                Text(
                    text = "Recent Transactions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            if (recentTransactions.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No transactions yet",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            } else {
                items(recentTransactions) { transaction ->
                    TransactionItem(
                        transaction = transaction,
                        onClick = { onTransactionClick(transaction.id.toString()) }
                    )
                }
            }
        }
    }
}

@Composable
fun ActionButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color
) {
    Button(
        onClick = onClick,
        modifier = Modifier
//            .weight(1f)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
            Text(text)
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onDepositClick = {},
        onWithdrawClick = {},
        onTransactionClick = {}
    )
}
