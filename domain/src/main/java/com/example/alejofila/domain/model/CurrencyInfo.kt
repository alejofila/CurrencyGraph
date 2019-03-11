package com.example.alejofila.domain.model


data class CurrencyHistory(
    val baseSymbol: String,
    val startDate: String,
    val endDate: String,
    val currencyInfo: Map<String, Map<String, Double>>,
    val symbols: String
)



