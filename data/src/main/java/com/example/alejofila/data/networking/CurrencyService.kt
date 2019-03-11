package com.example.alejofila.data.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CurrencyService {

    const val API_URL = "https://api.exchangeratesapi.io/"

    fun getCurrencyService(): CurrencyExchangeApi {
        val retrofit = Retrofit.Builder().baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CurrencyExchangeApi::class.java)
    }

}