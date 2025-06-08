package dev.tanujn45.currencyx.data.model

import dev.tanujn45.currencyx.BuildConfig
import java.net.URI

data class NewsResponse(
    val articles: List<Article>
)

data class Article(
    val title: String, val source: Source, val image: String?, val url: String
)

fun Article.toUi(logoToken: String = BuildConfig.LOGODEV_TOKEN): ArticleUi {
    val domain = runCatching { URI(url).host.removePrefix("www.") }.getOrDefault("")
    val logo = "https://img.logo.dev/$domain?token=$logoToken"
    return ArticleUi(title, source.name, logo, image, url)
}

data class ArticleUi(
    val title: String,
    val sourceName: String,
    val sourceIconUrl: String,
    val imageUrl: String?,
    val articleUrl: String
)

data class Source(
    val name: String,
    val url: String
)

sealed interface NewsUiState {
    data class Success(val articles: List<ArticleUi>) : NewsUiState
    data class Error(val message: String) : NewsUiState
    object Loading : NewsUiState
}

