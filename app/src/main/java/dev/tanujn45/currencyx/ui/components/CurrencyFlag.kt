package dev.tanujn45.currencyx.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun CurrencyFlag(
    currencyCode: String,
    modifier: Modifier = Modifier
) {
    val flagUrl = getCurrencyFlagUrl(currencyCode)

    AsyncImage(
        model = flagUrl,
        contentDescription = "$currencyCode flag",
        modifier = modifier
            .clip(CircleShape)
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                CircleShape
            ),
        contentScale = ContentScale.Crop,
        /*  error = {
              // Fallback to currency code if flag fails to load
              Box(
                  modifier = Modifier
                      .fillMaxSize()
                      .background(
                          MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                          CircleShape
                      ),
                  contentAlignment = Alignment.Center
              ) {
                  Text(
                      text = currencyCode.take(2),
                      style = MaterialTheme.typography.labelSmall,
                      fontWeight = FontWeight.Bold,
                      color = MaterialTheme.colorScheme.primary
                  )
              }
          }*/
    )
}

fun getCurrencyFlagUrl(currencyCode: String): String {
    // Map currency codes to country codes for flag display
    val currencyToCountry = mapOf(
        "USD" to "us",
        "EUR" to "eu",
        "GBP" to "gb",
        "JPY" to "jp",
        "AUD" to "au",
        "CAD" to "ca",
        "CHF" to "ch",
        "CNY" to "cn",
        "INR" to "in",
        "KRW" to "kr",
        "SGD" to "sg",
        "HKD" to "hk",
        "NOK" to "no",
        "SEK" to "se",
        "DKK" to "dk",
        "PLN" to "pl",
        "CZK" to "cz",
        "HUF" to "hu",
        "RUB" to "ru",
        "BRL" to "br",
        "MXN" to "mx",
        "ZAR" to "za",
        "TRY" to "tr",
        "ILS" to "il",
        "AED" to "ae",
        "SAR" to "sa",
        "EGP" to "eg",
        "THB" to "th",
        "VND" to "vn",
        "IDR" to "id",
        "MYR" to "my",
        "PHP" to "ph",
        "NZD" to "nz"

    )

    val countryCode = currencyToCountry[currencyCode] ?: currencyCode.take(2).lowercase()

    // Using flagcdn.com for high-quality flag images
    return "https://flagcdn.com/$countryCode.svg"
}
