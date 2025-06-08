package dev.tanujn45.currencyx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tanujn45.currencyx.utils.CurrencyItem
import dev.tanujn45.currencyx.utils.getAllCurrencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn


class CurrencyPickerViewModel : ViewModel() {
    private val all: List<CurrencyItem> = getAllCurrencies()
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    val filtered: StateFlow<List<CurrencyItem>> = _query
        .debounce(200)
        .mapLatest { q ->
            if (q.isBlank()) all
            else all.filter { item ->
                item.code.contains(q, ignoreCase = true)
                        || item.name.contains(q, ignoreCase = true)
                        || item.country.contains(q, ignoreCase = true)
            }
        }
        .flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, SharingStarted.Lazily, all)

    fun onQueryChange(q: String) {
        _query.value = q
    }
}
