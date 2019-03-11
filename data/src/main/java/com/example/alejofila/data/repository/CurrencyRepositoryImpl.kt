package com.example.alejofila.data.repository

import com.example.alejofila.data.mapper.CurrencyHistoryMapper
import com.example.alejofila.data.networking.CurrencyExchangeApi
import com.example.alejofila.domain.model.CurrencyHistory
import com.example.alejofila.domain.repository.CurrencyHistoryRepository
import org.threeten.bp.LocalDate
import java.lang.IllegalStateException

class CurrencyRepositoryImpl(private val api: CurrencyExchangeApi) : CurrencyHistoryRepository {
    override fun getCurrencyHistoryWithDates(
        startDate: LocalDate,
        endDate: LocalDate,
        symbols: String
    ): CurrencyHistory {
        if(symbols.isEmpty()){
            throw IllegalStateException("Can't call getCurrencyHistoryWithDates if symbols is empty")
        }
        if(startDate.isAfter(endDate)){
            throw IllegalStateException("Can't call getCurrencyHistoryWithDates if startDate is after endDate")

        }
        val currencyHistoryData =
            (api.getHistoricalCurrency(startDate.toString(), endDate.toString()).execute().body()!!)
        return CurrencyHistoryMapper.fromDataToDomain(
            currencyHistoryData, symbols
        )

    }

}