package dev.tanujn45.currencyx.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun CurrencyDropdown(
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val currencies = listOf("USD", "EUR", "JPY", "GBP", "INR") // replace with dynamic if needed

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedCurrency)
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            currencies.forEach {
                DropdownMenuItem(text = { Text(it) }, onClick = {
                    onCurrencySelected(it)
                    expanded = false
                })
            }
        }
    }
}
