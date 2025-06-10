package dev.tanujn45.currencyx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tanujn45.currencyx.data.model.NewsUiState
import dev.tanujn45.currencyx.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val repo = NewsRepository()
    private val _newsArticles = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val newsArticles: StateFlow<NewsUiState> = _newsArticles

    init {
        loadNews("USD", "EUR")
    }

    fun loadNews(base: String, target: String) = viewModelScope.launch {
        _newsArticles.value = NewsUiState.Loading

        repo.forexNews(base, target).onSuccess { list ->
            _newsArticles.value = NewsUiState.Success(list)
        }.onFailure { throwable ->
            _newsArticles.value = NewsUiState.Error(throwable.message ?: "Unknown error")
        }
    }
}
