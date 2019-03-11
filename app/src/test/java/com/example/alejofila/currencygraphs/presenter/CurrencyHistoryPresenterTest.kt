package com.example.alejofila.currencygraphs.presenter

import com.example.alejofila.currencygraphs.common.mapper.CurrencyExchangeUiModelMapper
import com.example.alejofila.data.mapper.CurrencyHistoryMapper
import com.example.alejofila.data.networking.model.CurrencyHistoryResponse
import com.example.alejofila.domain.usecase.GetCurrencyInfoPeriodOfTimeUseCase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.internal.verification.VerificationModeFactory
import org.mockito.junit.MockitoJUnitRunner
import org.threeten.bp.LocalDate


class CurrencyHistoryPresenterTest {

    @Mock
    lateinit var useCase: GetCurrencyInfoPeriodOfTimeUseCase
    @Mock
    lateinit var view: CurrencyHistoryView
    lateinit var presenter: CurrencyHistoryPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = CurrencyHistoryPresenter(useCase, Dispatchers.Unconfined, Dispatchers.Unconfined)
        presenter.view = view
    }

    @Test
    fun `when previouDays is 0  should call showEmptyDaysError()`() {
        runBlocking {
            presenter.loadCurrencyHistory("0", LocalDate.now())
            Mockito.verify(view).showEmptyDaysError()
        }
    }

    @Test
    fun `when previousDays is less than 0 should call showEmptyDaysError()`() {
        runBlocking {
            presenter.loadCurrencyHistory("-10", LocalDate.now())
            Mockito.verify(view).showEmptyDaysError()
        }
    }

    @Test
    fun `when previousDays is greater than 1 should call showHistoryGraph`() {
        val jsonString =
            "{\"rates\":{\"2019-03-06\":{\"USD\":1.1305},\"2019-03-01\":{\"USD\":1.1383},\"2019-03-04\":{\"USD\":1.1337},\"2019-03-07\":{\"USD\":1.1271},\"2019-03-05\":{\"USD\":1.1329}},\"end_at\":\"2019-03-07\",\"base\":\"EUR\",\"start_at\":\"2019-03-01\"}"
        val gson = Gson()
        val response = gson.fromJson(jsonString, CurrencyHistoryResponse::class.java)
        val currencyHistoryDomain = CurrencyHistoryMapper.fromDataToDomain(response, "USD")
        val currencyUiModel = CurrencyExchangeUiModelMapper.fromDomainToUiModel(currencyHistoryDomain)
        runBlocking {
            Mockito.`when`(useCase(LocalDate.parse("2019-03-01"), LocalDate.parse("2019-03-07"), "USD"))
                .thenReturn(currencyHistoryDomain)
        }
        presenter.loadCurrencyHistory("7", LocalDate.now())

        Mockito.verify(view).showHistoryGraph(currencyUiModel)

    }


}