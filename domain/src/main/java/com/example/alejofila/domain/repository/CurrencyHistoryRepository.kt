package com.example.alejofila.domain.repository

import com.example.alejofila.domain.model.CurrencyHistory
import org.threeten.bp.LocalDate

interface CurrencyHistoryRepository {
    fun getCurrencyHistoryWithDates(startDate: LocalDate, endDate: LocalDate, symbols: String): CurrencyHistory
}