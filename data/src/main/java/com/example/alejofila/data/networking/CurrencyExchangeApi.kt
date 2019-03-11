package com.example.alejofila.data.networking


import com.example.alejofila.data.networking.model.CurrencyHistoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val EUR_SYMBOL ="EUR"
const val USD_SYMBOL ="USD"

interface CurrencyExchangeApi {
    @GET("history")
    fun getHistoricalCurrency(
        @Query("start_at") startAt: String,
        @Query("end_at") endAt: String,
        @Query("symbols") symbols: String =  USD_SYMBOL,
        @Query("base") base: String = EUR_SYMBOL

    ): Call<CurrencyHistoryResponse>
}