package com.example.alejofila.domain.usecase

import com.example.alejofila.domain.model.CurrencyHistory
import com.example.alejofila.domain.repository.CurrencyHistoryRepository
import org.threeten.bp.LocalDate

interface GetCurrencyInfoPeriodOfTimeUseCase {
    suspend operator fun invoke(startDate: LocalDate, endDate: LocalDate, symbols: String): CurrencyHistory
}

class GetCurrencyInfoPeriodOfTimeUseCaseImpl(private val repository: CurrencyHistoryRepository) :
    GetCurrencyInfoPeriodOfTimeUseCase {
    override suspend fun invoke(startDate: LocalDate, endDate: LocalDate, symbols: String): CurrencyHistory {
        if(startDate.isAfter(endDate)){
            throw IllegalStateException("Start date must be before endDate")
        }
        val history = repository.getCurrencyHistoryWithDates(startDate, endDate, symbols)
        val sortedMap = history.currencyInfo.toSortedMap(Comparator { key1, key2 ->
            val date1 = LocalDate.parse(key1)
            val date2 = LocalDate.parse(key2)
            when {
                date1.isAfter(date2) -> return@Comparator 1
                date1.isBefore(date2) -> return@Comparator -1
                else -> return@Comparator 0
            }
        })
        return history.copy(currencyInfo = sortedMap)
    }
}
