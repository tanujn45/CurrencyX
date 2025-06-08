package dev.tanujn45.currencyx.data.api

import dev.tanujn45.currencyx.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {

    @GET("search")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("lang") lang: String = "en",
        @Query("max") max: Int = 10,
        @Query("apikey") apiKey: String
    ): NewsResponse
}
