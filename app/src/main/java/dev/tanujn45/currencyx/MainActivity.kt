package dev.tanujn45.currencyx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.tanujn45.currencyx.ui.screens.home.HomeScreen
import dev.tanujn45.currencyx.ui.theme.CurrencyXTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyXTheme {
                HomeScreen()
            }
        }
    }
}
