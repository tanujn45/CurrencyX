package dev.tanujn45.currencyx.ui.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tanujn45.currencyx.utils.CurrencyPoint
import dev.tanujn45.currencyx.viewmodel.CurrencyViewModel
import dev.tanujn45.currencyx.viewmodel.NewsViewModel
import dev.tanujn45.currencyx.viewmodel.Selector
import dev.tanujn45.currencyx.viewmodel.UiState


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    newsViewModel: NewsViewModel = viewModel(),
    currencyViewModel: CurrencyViewModel = viewModel(),
) {
    val newsUiState by newsViewModel.newsArticles.collectAsState()
    val ratesUi by currencyViewModel.multipleDayRates.collectAsState()

    val fromCurr by currencyViewModel.fromCurrency.collectAsState()
    val fromAmt by currencyViewModel.fromAmount.collectAsState()
    val toCurr by currencyViewModel.toCurrency.collectAsState()
    val toAmt by currencyViewModel.toAmount.collectAsState()
    val changePercent by currencyViewModel.changePercent.collectAsState()

    val points: List<CurrencyPoint> = when (ratesUi) {
        is UiState.Success -> (ratesUi as UiState.Success).data
        else -> emptyList()
    }
    val exchangeRate = points.lastOrNull()?.value ?: 0f
    val lastUpdated = points.lastOrNull()?.date ?: "—"

    var activeSelector by remember { mutableStateOf<Selector?>(null) }

    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Text(text = "Welcome", style = MaterialTheme.typography.headlineLarge)
        }
        item {
            ConverterSection(
                fromCurrency = fromCurr,
                toCurrency = toCurr,
                fromAmount = fromAmt,
                toAmount = toAmt,
                exchangeRate = exchangeRate,
                lastUpdated = lastUpdated,
                points = points,
                changePercent = changePercent,
                onFromCurrencyClick = { activeSelector = Selector.FROM },
                onToCurrencyClick = { activeSelector = Selector.TO },
                onSwapClick = {
                    currencyViewModel.setFrom(toCurr, toAmt)
                    currencyViewModel.setTo(fromCurr)
                }
            )
            if (activeSelector != null) {
                CurrencySelectorBottomSheet(
                    isVisible = true,
                    hideInput = activeSelector == Selector.TO,
                    currentAmount = if (activeSelector == Selector.FROM) fromAmt else toAmt,
                    selectedCurrency = if (activeSelector == Selector.FROM) fromCurr else toCurr,
                    onDismiss = { activeSelector = null },
                    onCurrencySelected = { currencyItem, newAmount ->
                        when (activeSelector) {
                            Selector.FROM -> currencyViewModel.setFrom(currencyItem, newAmount)
                            Selector.TO -> currencyViewModel.setTo(currencyItem)
                            null -> TODO()
                        }
                        newsViewModel.loadNews(
                            fromCurr.code, toCurr.code
                        )
                        activeSelector = null
                    })
            }
        }

        item {
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Latest News",
                modifier = Modifier.padding(bottom = 16.dp),
                style = MaterialTheme.typography.titleLarge
            )
        }

        newsSection(newsUiState)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}

