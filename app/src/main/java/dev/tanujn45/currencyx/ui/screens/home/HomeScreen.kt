package dev.tanujn45.currencyx.ui.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tanujn45.currencyx.ui.components.CurrencyDropdown

// Data model for news
data class NewsArticle(val title: String, val publishedAt: String)

@Composable
fun HomeScreen() {
    val baseCurrency = remember { mutableStateOf("USD") }
    val targetCurrency = remember { mutableStateOf("EUR") }

    val sampleNews = remember {
        listOf(
            NewsArticle("USD strengthens on Fed rate talk", "2025-05-18"),
            NewsArticle("EUR faces pressure as growth slows", "2025-05-17"),
            NewsArticle("Forex market jittery before G7 meet", "2025-05-16")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Currency Chart Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CurrencyDropdown(
                        selectedCurrency = baseCurrency.value,
                        onCurrencySelected = { baseCurrency.value = it }
                    )
                    Text("vs", modifier = Modifier.align(Alignment.CenterVertically))
                    CurrencyDropdown(
                        selectedCurrency = targetCurrency.value,
                        onCurrencySelected = { targetCurrency.value = it }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                LineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    points = mockChartData
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Top Forex News",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        sampleNews.forEach { article ->
            NewsCard(article)
        }
    }
}

@Composable
fun ChartPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text("Chart Placeholder", color = Color.DarkGray)
    }
}

@Composable
fun NewsCard(article: NewsArticle) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = article.publishedAt,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}

data class CurrencyPoint(val time: String, val value: Float)

val mockChartData = listOf(
    CurrencyPoint("09:00", 1.10f),
    CurrencyPoint("11:00", 1.13f),
    CurrencyPoint("13:00", 1.12f),
    CurrencyPoint("15:00", 1.15f),
    CurrencyPoint("17:00", 1.14f),
    CurrencyPoint("19:00", 1.18f)
)

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    points: List<CurrencyPoint>
) {
    if (points.isEmpty()) {
        Box(
            modifier = modifier
                .background(Color.LightGray, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("No chart data", color = Color.DarkGray)
        }
        return
    }

    val maxValue = points.maxOf { it.value }
    val minValue = points.minOf { it.value }
    val valueRange = if (maxValue - minValue == 0f) 1f else maxValue - minValue

    Canvas(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        val spacing = size.width / (points.size - 1).toFloat()
        val chartHeight = size.height

        val path = Path()

        points.forEachIndexed { index, point ->
            val x = index * spacing
            val normalized = (point.value - minValue) / valueRange
            val y = chartHeight - (normalized * chartHeight)

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = Color(0xFF4CAF50),
            style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
        )

        points.forEachIndexed { index, point ->
            val x = index * spacing
            val normalized = (point.value - minValue) / valueRange
            val y = chartHeight - (normalized * chartHeight)

            drawCircle(
                color = Color(0xFF1B5E20),
                radius = 5.dp.toPx(),
                center = Offset(x, y)
            )
        }
    }
}
