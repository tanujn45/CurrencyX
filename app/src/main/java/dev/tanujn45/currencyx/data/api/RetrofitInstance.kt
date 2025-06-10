package dev.tanujn45.currencyx.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val GNEWS_BASE_URL = "https://gnews.io/api/v4/"
    private const val EXCHANGE_RATE_BASE_URL = "https://api.exchangerate.host/"

    val gNewsApi: NewsApiService by lazy {
        Retrofit.Builder().baseUrl(GNEWS_BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build().create(NewsApiService::class.java)
    }

    val exchangeRateApi: TimeFrameRateApiService by lazy {
        Retrofit.Builder().baseUrl(EXCHANGE_RATE_BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build().create(TimeFrameRateApiService::class.java)
    }
}
