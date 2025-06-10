package dev.tanujn45.currencyx.data.api

import dev.tanujn45.currencyx.data.model.TimeframeRatesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TimeFrameRateApiService {
    @GET("timeframe")
    suspend fun getTimeFrameRates(
        @Query("access_key") accessKey: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("source") source: String? = null,
        @Query("currencies") currencies: String? = null
    ): TimeframeRatesResponse
}