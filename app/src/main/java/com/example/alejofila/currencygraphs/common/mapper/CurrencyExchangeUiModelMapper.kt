package com.example.alejofila.currencygraphs.common.mapper

import com.example.alejofila.currencygraphs.common.model.CurrencyDetailsUiModel
import com.example.alejofila.currencygraphs.common.model.CurrencyExchangeUiModel
import com.example.alejofila.domain.model.CurrencyHistory
import com.github.mikephil.charting.data.Entry
import java.util.concurrent.atomic.AtomicInteger

object CurrencyExchangeUiModelMapper {
    fun fromDomainToUiModel(
        currencyHistoryDomain: CurrencyHistory
    ): CurrencyExchangeUiModel {
        val listOfExchangeChart = mutableListOf<Entry>()
        val index = AtomicInteger(0)
        val listOfDates = mutableListOf<String>()
        currencyHistoryDomain.currencyInfo.forEach { keyEntry ->
            listOfDates.add(keyEntry.key)
            keyEntry.value.forEach { entry ->
                listOfExchangeChart.add(Entry(index.getAndIncrement().toFloat(), entry.value.toFloat()))
            }
        }
        val currencyExchangeUiModel = CurrencyExchangeUiModel(
            currencyHistoryDomain.baseSymbol,
            currencyHistoryDomain.startDate,
            currencyHistoryDomain.endDate,
            currencyInfoDetils = CurrencyDetailsUiModel(
                currencyHistoryDomain.symbols,
                listOfDates,
                listOfExchangeChart
            )
        )
        return currencyExchangeUiModel
    }


}