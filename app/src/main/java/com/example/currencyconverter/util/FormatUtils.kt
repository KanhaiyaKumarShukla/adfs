package com.example.currencyconverter.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Formats a double value as a currency string with the given currency code.
 * @param amount The amount to format
 * @param currencyCode The currency code (e.g., "USD", "EUR", "INR")
 * @return Formatted currency string with symbol
 */
fun formatCurrency(amount: Double, currencyCode: String = "INR"): String {
    return try {
        val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
        val currency = Currency.getInstance(currencyCode.uppercase())
        format.currency = currency
        format.maximumFractionDigits = if (currency.defaultFractionDigits > 0) 2 else 0
        format.format(amount)
    } catch (e: Exception) {
        // Fallback for unsupported currency codes
        "$currencyCode $amount"
    }
}

/**
 * Formats a timestamp to a readable date string.
 * @param timestamp The timestamp in milliseconds
 * @return Formatted date string (e.g., "Oct 6, 2023")
 */
fun formatDate(timestamp: Long): String {
    return SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(Date(timestamp))
}

/**
 * Formats a timestamp to a readable time string.
 * @param timestamp The timestamp in milliseconds
 * @return Formatted time string (e.g., "2:30 PM")
 */
fun formatTime(timestamp: Long): String {
    return SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date(timestamp))
}

/**
 * Formats a double value to a string with 2 decimal places.
 * @param value The value to format
 * @return Formatted string with 2 decimal places
 */
fun formatDecimal(value: Double): String {
    return String.format("%.2f", value)
}

/**
 * Formats a transaction amount with a + or - sign based on the transaction type.
 * @param amount The transaction amount
 * @param isDeposit True if it's a deposit, false for withdrawal
 * @param currencyCode The currency code (default: "INR")
 * @return Formatted string with sign (e.g., "+$100.00" or "-₹5,000.00")
 */
fun formatTransactionAmount(amount: Double, isDeposit: Boolean, currencyCode: String = "INR"): String {
    val sign = if (isDeposit) "+" else "-"
    return "$sign${formatCurrency(kotlin.math.abs(amount), currencyCode)}"
}
