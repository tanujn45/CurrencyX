package dev.tanujn45.currencyx.data.repository

import dev.tanujn45.currencyx.BuildConfig
import dev.tanujn45.currencyx.data.api.RetrofitInstance
import dev.tanujn45.currencyx.data.model.ArticleUi
import dev.tanujn45.currencyx.data.model.toUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository {
    private val api = RetrofitInstance.api

    suspend fun forexNews(
        baseCurrency: String, targetCurrency: String
    ): Result<List<ArticleUi>> = withContext(Dispatchers.IO) {

        runCatching {
            val query = "$baseCurrency $targetCurrency forex OR \"exchange rate\""

            api.searchNews(
                query = query, apiKey = BuildConfig.GNEWS_API_KEY
            ).articles.map { it.toUi(BuildConfig.LOGODEV_TOKEN) }
        }
    }
}
