package dev.tanujn45.currencyx.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.tanujn45.currencyx.ui.components.DropdownButton
import dev.tanujn45.currencyx.ui.components.LineChart
import dev.tanujn45.currencyx.utils.CurrencyItem
import dev.tanujn45.currencyx.utils.CurrencyPoint
import dev.tanujn45.currencyx.utils.NUM_DAYS

@Composable
fun ConverterSection(
    fromCurrency: CurrencyItem,
    toCurrency: CurrencyItem,
    fromAmount: String = "1",
    toAmount: String,
    exchangeRate: Float,
    changePercent: Float,
    lastUpdated: String,
    onFromCurrencyClick: () -> Unit = {},
    onToCurrencyClick: () -> Unit = {},
    onSwapClick: () -> Unit = {},
    points: List<CurrencyPoint>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${fromCurrency.code} to ${toCurrency.code}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "1 ${fromCurrency.code} = $exchangeRate ${toCurrency.code}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = if (changePercent >= 0) "+$changePercent%" else "$changePercent%",
                        style = MaterialTheme.typography.labelMedium,
                        color = if (changePercent >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Updated $lastUpdated",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // From currency
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "From",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    DropdownButton(
                        currency = fromCurrency, amount = fromAmount, onClick = onFromCurrencyClick
                    )
                }

                // Swap button
                IconButton(
                    onClick = onSwapClick, modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.SwapHoriz,
                        contentDescription = "Swap currencies",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // To currency
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "To",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    DropdownButton(
                        currency = toCurrency, amount = toAmount, onClick = onToCurrencyClick
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Chart section
            Text(
                text = "Exchange Rate Trend ($NUM_DAYS days)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp), points = points
            )
        }
    }
}
