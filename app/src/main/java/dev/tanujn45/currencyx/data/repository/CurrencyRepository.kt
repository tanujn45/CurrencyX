package dev.tanujn45.currencyx.data.repository

import dev.tanujn45.currencyx.BuildConfig
import dev.tanujn45.currencyx.data.api.RetrofitInstance

class CurrencyRepository {
    private val timeframeApi = RetrofitInstance.exchangeRateApi

    suspend fun getMultipleDayRates(
        start: String, end: String, base: String, target: String
    ) = timeframeApi.getTimeFrameRates(
        accessKey = BuildConfig.EXCHANGE_RATE_HOST_API_KEY,
        startDate = start,
        endDate = end,
        source = base,
        currencies = target
    )
}