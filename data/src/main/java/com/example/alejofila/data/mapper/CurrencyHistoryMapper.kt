package com.example.alejofila.data.mapper

import com.example.alejofila.data.networking.model.CurrencyHistoryResponse
import com.example.alejofila.domain.model.CurrencyHistory


object CurrencyHistoryMapper {
    fun
            fromDataToDomain(currencyHistoryData: CurrencyHistoryResponse, symbolString: String): CurrencyHistory {
        return CurrencyHistory(
            currencyHistoryData.baseSymbol,
            currencyHistoryData.startDate,
            currencyHistoryData.endDate,
            currencyHistoryData.currencyInfo,
            symbolString
        )
    }
}
