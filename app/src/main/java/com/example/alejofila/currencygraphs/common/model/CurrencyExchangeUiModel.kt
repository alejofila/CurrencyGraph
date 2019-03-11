package com.example.alejofila.currencygraphs.common.model

import com.github.mikephil.charting.data.Entry

class CurrencyExchangeUiModel(
    val baseSymbol: String,
    val startDate: String,
    val endDate: String,
    val currencyInfoDetils: CurrencyDetailsUiModel
)

class CurrencyDetailsUiModel(
    val currency: String,
    val listOfDates: List<String>,
    val listOfExchange: List<Entry>
)