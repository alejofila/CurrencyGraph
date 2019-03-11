package com.example.alejofila.data.networking.model

import com.google.gson.annotations.SerializedName

data class CurrencyInfoData(
    val values: Map<String, Map<String,Double>>
)



data class CurrencyHistoryResponse(
    @SerializedName("base") val baseSymbol: String,
    @SerializedName("start_at") val startDate: String,
    @SerializedName("end_at") val endDate: String,
    @SerializedName("rates") val currencyInfo:  Map<String, Map<String,Double>>
)