package dev.tanujn45.currencyx.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.tanujn45.currencyx.data.model.ArticleUi
import dev.tanujn45.currencyx.data.model.NewsUiState


fun LazyListScope.newsSection(ui: NewsUiState) {
    when (ui) {
        NewsUiState.Loading -> {
            item {
                Text(
                    "Loading...", modifier = Modifier.padding(24.dp)
                )
            }
        }

        is NewsUiState.Error -> {
            item {
                Text(
                    ui.message, modifier = Modifier.padding(24.dp)
                )
            }
        }

        is NewsUiState.Success -> {
            val list = ui.articles
            if (list.isEmpty()) {
                item {
                    Text(
                        "No forex news today.", modifier = Modifier.padding(24.dp)
                    )
                }
            } else {
                items(list) { article: ArticleUi ->
                    NewsArticleCard(article)
                }
            }
        }
    }
}


@Composable
private fun NewsArticleCard(article: ArticleUi) {
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            uriHandler.openUri(article.articleUrl)
        }) {
        Column {
            // Main article image
            AsyncImage(
                model = article.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )

            // Content section
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Source information
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    AsyncImage(
                        model = article.sourceIconUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = article.sourceName.uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Article title
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun NewsArticleCardPreview() {
    NewsArticleCard(
        article = ArticleUi(
            title = "This is a title",
            sourceName = "New York Times",
            sourceIconUrl = "nytimes.com",
            imageUrl = "https://images.pexels.com/photos/31933377/pexels-photo-31933377/free-photo-of-majestic-cheetah-in-namibian-wildlife-reserve.jpeg",
            articleUrl = "https://www.nytimes.com/2025/05/27/us/politics/trump-pardon-paul-walczak-tax-crimes.html"
        )
    )
}
