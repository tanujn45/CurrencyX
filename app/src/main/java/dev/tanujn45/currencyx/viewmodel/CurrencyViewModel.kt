package dev.tanujn45.currencyx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tanujn45.currencyx.data.repository.CurrencyRepository
import dev.tanujn45.currencyx.utils.CurrencyItem
import dev.tanujn45.currencyx.utils.CurrencyPoint
import dev.tanujn45.currencyx.utils.NUM_DAYS
import dev.tanujn45.currencyx.utils.getAllCurrencies
import dev.tanujn45.currencyx.utils.roundToTwoDecimalPlaces
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

enum class Selector { FROM, TO }

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class CurrencyViewModel : ViewModel() {
    private val repo = CurrencyRepository()

    private val _fromCurrency = MutableStateFlow(getAllCurrencies().first())
    val fromCurrency: StateFlow<CurrencyItem> = _fromCurrency

    private val _fromAmount = MutableStateFlow("1000.00")
    val fromAmount: StateFlow<String> = _fromAmount

    private val _toCurrency = MutableStateFlow(getAllCurrencies()[8])
    val toCurrency: StateFlow<CurrencyItem> = _toCurrency

    private val _toAmount = MutableStateFlow("920.50")
    val toAmount: StateFlow<String> = _toAmount

    private val _changePercent = MutableStateFlow(0f)
    val changePercent: StateFlow<Float> = _changePercent

    private val _multipleDayRates = MutableStateFlow<UiState<List<CurrencyPoint>>>(UiState.Loading)
    val multipleDayRates: StateFlow<UiState<List<CurrencyPoint>>> = _multipleDayRates

    init {
        // initial load
        refreshMultipleDayRates()
    }

    // (You can also expose exchangeRate, lastUpdated, points, etc. here)
    fun setFrom(currency: CurrencyItem, amount: String) {
        _fromCurrency.value = currency
        _fromAmount.value = amount
        refreshMultipleDayRates()
    }

    fun setTo(currency: CurrencyItem) {
        _toCurrency.value = currency
        refreshMultipleDayRates()
    }

    private fun refreshMultipleDayRates() = viewModelScope.launch {
        _multipleDayRates.value = UiState.Loading

        // compute today & six days ago
        val today = LocalDate.now()
        val endDate = today.format(DateTimeFormatter.ISO_DATE)
        val startDate = today.minusDays(NUM_DAYS.toLong()).format(DateTimeFormatter.ISO_DATE)

        val base = fromCurrency.value.code
        val target = toCurrency.value.code
        val pairCode = "${base}${target}"

        runCatching {
            repo.getMultipleDayRates(
                startDate, endDate, base, target
            )
        }.onSuccess { resp ->
            // flatten into a List<CurrencyPoint>, sorted by date
            val pts = resp.quotes.entries.mapNotNull { (date, rates) ->
                rates[pairCode]?.let { rate ->
                    CurrencyPoint(date, rate.toFloat())
                }
            }.sortedBy { it.date }
            val change = if (pts.size >= 2) {
                val first = pts.first().value
                val last = pts.last().value
                (last - first) / first * 100f
            } else 0f
            val latestRate = pts.lastOrNull()?.value ?: 0f

            val fromValue = fromAmount.value.toFloatOrNull() ?: 0f

            val toValue = roundToTwoDecimalPlaces(fromValue * latestRate)

            _toAmount.value = "%.2f".format(toValue)
            _changePercent.value = roundToTwoDecimalPlaces(change)
            _multipleDayRates.value = UiState.Success(pts)
        }.onFailure { err ->
            _multipleDayRates.value = UiState.Error(err.localizedMessage ?: "Unknown error")
        }
    }
}
