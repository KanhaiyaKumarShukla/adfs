package com.example.currencyconverter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.data.local.entity.TransactionEntity
import com.example.currencyconverter.util.formatCurrency
import com.example.currencyconverter.util.formatDate

@Composable
fun TransactionItem(
    transaction: TransactionEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDeposit = transaction.type == TransactionEntity.TransactionType.DEPOSIT
    val icon = if (isDeposit) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward
    val iconTint = if (isDeposit) Color(0xFF4CAF50) else Color(0xFFF44336)
    val amountColor = if (isDeposit) Color(0xFF4CAF50) else Color(0xFFF44336)
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = if (isDeposit) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                        shape = MaterialTheme.shapes.small
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = if (isDeposit) "Deposit" else "Withdrawal",
                    tint = iconTint
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Transaction Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = transaction.description.ifEmpty { 
                        if (isDeposit) "Deposit" else "Withdrawal" 
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                
                Text(
                    text = formatDate(transaction.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            
            // Amount
            Text(
                text = "${if (isDeposit) "+" else "-"} ${formatCurrency(transaction.amountInInr)}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = amountColor
            )
        }
    }
}
