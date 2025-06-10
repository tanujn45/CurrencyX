package dev.tanujn45.currencyx.data.model

import com.google.gson.annotations.SerializedName

data class TimeframeRatesResponse(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val timeframe: Boolean,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("end_date") val endDate: String,
    val source: String,
    // each key is a date, value is a map of "USD{CUR}" → rate
    val quotes: Map<String, Map<String, Double>>
)
