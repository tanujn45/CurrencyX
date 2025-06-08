package dev.tanujn45.currencyx.utils

// Data class for currency
data class CurrencyItem(
    val code: String, val name: String, val country: String, val symbol: String = ""
)

// helper
fun CurrencyItem.matches(q: String) =
    code.contains(q, true) || name.contains(q, true) || country.contains(q, true)

// Sample currency data
fun getAllCurrencies(): List<CurrencyItem> {
    return listOf(
        CurrencyItem("USD", "US Dollar", "United States", "$"),
        CurrencyItem("EUR", "Euro", "European Union", "€"),
        CurrencyItem("GBP", "British Pound", "United Kingdom", "£"),
        CurrencyItem("JPY", "Japanese Yen", "Japan", "¥"),
        CurrencyItem("AUD", "Australian Dollar", "Australia", "A$"),
        CurrencyItem("CAD", "Canadian Dollar", "Canada", "C$"),
        CurrencyItem("CHF", "Swiss Franc", "Switzerland", "CHF"),
        CurrencyItem("CNY", "Chinese Yuan", "China", "¥"),
        CurrencyItem("INR", "Indian Rupee", "India", "₹"),
        CurrencyItem("KRW", "South Korean Won", "South Korea", "₩"),
        CurrencyItem("SGD", "Singapore Dollar", "Singapore", "S$"),
        CurrencyItem("HKD", "Hong Kong Dollar", "Hong Kong", "HK$"),
        CurrencyItem("NOK", "Norwegian Krone", "Norway", "kr"),
        CurrencyItem("SEK", "Swedish Krona", "Sweden", "kr"),
        CurrencyItem("DKK", "Danish Krone", "Denmark", "kr"),
        CurrencyItem("PLN", "Polish Złoty", "Poland", "zł"),
        CurrencyItem("CZK", "Czech Koruna", "Czech Republic", "Kč"),
        CurrencyItem("HUF", "Hungarian Forint", "Hungary", "Ft"),
        CurrencyItem("RUB", "Russian Ruble", "Russia", "₽"),
        CurrencyItem("BRL", "Brazilian Real", "Brazil", "R$"),
        CurrencyItem("MXN", "Mexican Peso", "Mexico", "$"),
        CurrencyItem("ZAR", "South African Rand", "South Africa", "R"),
        CurrencyItem("TRY", "Turkish Lira", "Turkey", "₺"),
        CurrencyItem("ILS", "Israeli Shekel", "Israel", "₪"),
        CurrencyItem("AED", "UAE Dirham", "United Arab Emirates", "د.إ"),
        CurrencyItem("SAR", "Saudi Riyal", "Saudi Arabia", "﷼"),
        CurrencyItem("EGP", "Egyptian Pound", "Egypt", "£"),
        CurrencyItem("THB", "Thai Baht", "Thailand", "฿"),
        CurrencyItem("VND", "Vietnamese Dong", "Vietnam", "₫"),
        CurrencyItem("IDR", "Indonesian Rupiah", "Indonesia", "Rp"),
        CurrencyItem("MYR", "Malaysian Ringgit", "Malaysia", "RM"),
        CurrencyItem("PHP", "Philippine Peso", "Philippines", "₱"),
        CurrencyItem("NZD", "New Zealand Dollar", "New Zealand", "NZ$")
    )
}

