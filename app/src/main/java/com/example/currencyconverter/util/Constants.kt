package com.example.currencyconverter.util

object Constants {
    const val BASE_URL = "https://v6.exchangerate-api.com/"
    const val API_KEY = "8cea16c76b5c2669eae114d1"
    
    // Shared Preferences Keys
    const val PREF_NAME = "currency_converter_prefs"
    
    // Navigation Routes
    const val SPLASH_SCREEN = "splash_screen"
    const val ONBOARDING_SCREEN = "onboarding_screen"
    const val HOME_SCREEN = "home_screen"
    const val DEPOSIT_SCREEN = "deposit_screen"
    const val WITHDRAW_SCREEN = "withdraw_screen"
    
    // Default values
    const val DEFAULT_CURRENCY = "INR"
    const val DEFAULT_BALANCE = 0.0
    
    // Animation durations
    const val SPLASH_DELAY = 2500L // 2.5 seconds
    
    // Supported currencies
    val SUPPORTED_CURRENCIES = listOf(
        "USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "INR"
    )
    
    // Currency symbols
    val CURRENCY_SYMBOLS = mapOf(
        "USD" to "$",
        "EUR" to "€",
        "GBP" to "£",
        "JPY" to "¥",
        "AUD" to "A$",
        "CAD" to "C$",
        "CHF" to "CHF",
        "CNY" to "¥",
        "INR" to "₹"
    )
}
