package com.example.alejofila.domain.usecase

import com.example.alejofila.domain.model.CurrencyHistory
import com.example.alejofila.domain.repository.CurrencyHistoryRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.threeten.bp.LocalDate

@RunWith(MockitoJUnitRunner::class)
class GetCurrencyInfoPeriodOfTimeUseCaseImplTest {
    lateinit var useCase: GetCurrencyInfoPeriodOfTimeUseCase

    @Mock
    lateinit var repository: CurrencyHistoryRepository

    @Before
    fun setup() {
        repository
        useCase = GetCurrencyInfoPeriodOfTimeUseCaseImpl(repository)
    }

    @Test(expected = IllegalStateException::class)
    fun `when startDate is after endDate should throw exception`() {
        runBlocking {
            useCase(LocalDate.now().plusDays(2), LocalDate.now(), "USD")
        }

    }

    @Test
    fun `when startDate is before endDate should return a valid CurrencyHistory`() {
        runBlocking {
            val startDate = LocalDate.now().minusDays(5)
            val endDate = LocalDate.now()
            val symbol = "USD"
            val mockCurrencyHistory = mock(CurrencyHistory::class.java)
            Mockito.`when`(repository.getCurrencyHistoryWithDates(startDate, endDate, symbol))
                .thenReturn(mockCurrencyHistory)
            Assert.assertEquals(useCase(LocalDate.now().plusDays(2), LocalDate.now(), "USD"),
                mockCurrencyHistory)
        }

    }
}

