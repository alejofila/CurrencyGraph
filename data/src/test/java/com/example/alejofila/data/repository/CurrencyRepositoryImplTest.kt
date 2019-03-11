package com.example.alejofila.data.repository

import com.example.alejofila.data.mapper.CurrencyHistoryMapper
import com.example.alejofila.data.networking.CurrencyExchangeApi
import com.example.alejofila.data.networking.model.CurrencyHistoryResponse
import com.example.alejofila.domain.repository.CurrencyHistoryRepository
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.threeten.bp.LocalDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class CurrencyRepositoryImplTest {

    lateinit var repository: CurrencyHistoryRepository

    @Mock
    lateinit var api: CurrencyExchangeApi

    @Before
    fun setUp() {
        repository = CurrencyRepositoryImpl(api)
    }

    @Test
    fun `when a valid startDate and endDate getCurrencyHistoryWithDates should return a valid domain object`() {
        val jsonString =
            "{\"rates\":{\"2019-03-06\":{\"USD\":1.1305},\"2019-03-01\":{\"USD\":1.1383},\"2019-03-04\":{\"USD\":1.1337},\"2019-03-07\":{\"USD\":1.1271},\"2019-03-05\":{\"USD\":1.1329}},\"end_at\":\"2019-03-07\",\"base\":\"EUR\",\"start_at\":\"2019-03-01\"}"
        val gson = Gson()
        val response = gson.fromJson(jsonString, CurrencyHistoryResponse::class.java)
        val call : Call<CurrencyHistoryResponse> = mock(Call::class.java) as Call<CurrencyHistoryResponse>
        val startDate = LocalDate.parse("2019-02-01")
        val endDate = LocalDate.parse("2019-02-05")
        val symbol ="USD"
        Mockito.`when`(call.execute()).thenReturn(Response.success(response))
        val currencyHistoryDomain = CurrencyHistoryMapper.fromDataToDomain(response, symbol)
        runBlocking {
            Mockito.`when`(api.getHistoricalCurrency(startDate.toString(), endDate.toString())).thenReturn(call)
            Assert.assertEquals(currencyHistoryDomain , repository.getCurrencyHistoryWithDates(startDate,endDate,symbol))


        }
    }
    @Test(expected = IllegalStateException::class)
    fun `when a startDate that is after endDate getCurrrencyHistoryWithDates should thrown an exception`(){
        repository.getCurrencyHistoryWithDates(LocalDate.parse("2019-03-04"), LocalDate.parse("2019-03-02"),"USD")
    }
    @Test(expected = IllegalStateException::class)
    fun `when symbol is an empty string, getCurrrencyHistoryWithDates should thrown an exception`(){
        repository.getCurrencyHistoryWithDates(LocalDate.parse("2019-03-04"), LocalDate.parse("2019-03-02"),"")
    }
}