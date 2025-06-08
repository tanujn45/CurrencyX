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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tanujn45.currencyx.viewmodel.NewsViewModel


@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: NewsViewModel = viewModel()) {
    val uiState by viewModel.newsArticles.collectAsState()

    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Text(text = "Welcome", style = MaterialTheme.typography.headlineLarge)
        }
        item { ConverterSection() }

        item {
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Latest News",
                modifier = Modifier.padding(bottom = 16.dp),
                style = MaterialTheme.typography.titleLarge
            )
        }

        newsSection(uiState)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}

