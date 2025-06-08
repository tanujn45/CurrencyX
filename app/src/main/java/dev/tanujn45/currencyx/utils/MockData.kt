package dev.tanujn45.currencyx.utils


// Same data class
data class CurrencyPoint(val date: String, val value: Float = 0f)

// USD → EUR reference rates, 1 – 30 Jan 2023
val usdJan2023 = listOf(
    CurrencyPoint("2023-01-01", 0.9375f), // Sun – uses 2 Jan rate
    CurrencyPoint("2023-01-02", 0.9375f),
    CurrencyPoint("2023-01-03", 0.9480f),
    CurrencyPoint("2023-01-04", 0.9427f),
    CurrencyPoint("2023-01-05", 0.9504f),
    CurrencyPoint("2023-01-06", 0.9378f),
    CurrencyPoint("2023-01-07", 0.9378f), // Sat
    CurrencyPoint("2023-01-08", 0.9378f), // Sun
    CurrencyPoint("2023-01-09", 0.9317f),
    CurrencyPoint("2023-01-10", 0.9314f),
    CurrencyPoint("2023-01-11", 0.9295f),
    CurrencyPoint("2023-01-12", 0.9218f),
    CurrencyPoint("2023-01-13", 0.9220f),
    CurrencyPoint("2023-01-14", 0.9220f), // Sat
    CurrencyPoint("2023-01-15", 0.9220f), // Sun
    CurrencyPoint("2023-01-16", 0.9240f),
    CurrencyPoint("2023-01-17", 0.9266f),
    CurrencyPoint("2023-01-18", 0.9266f),
    CurrencyPoint("2023-01-19", 0.9233f),
    CurrencyPoint("2023-01-20", 0.9210f),
    CurrencyPoint("2023-01-21", 0.9210f), // Sat
    CurrencyPoint("2023-01-22", 0.9210f), // Sun
    CurrencyPoint("2023-01-23", 0.9199f),
    CurrencyPoint("2023-01-24", 0.9186f),
    CurrencyPoint("2023-01-25", 0.9162f),
    CurrencyPoint("2023-01-26", 0.9181f),
    CurrencyPoint("2023-01-27", 0.9200f),
    CurrencyPoint("2023-01-28", 0.9200f), // Sat
    CurrencyPoint("2023-01-29", 0.9200f), // Sun
    CurrencyPoint("2023-01-30", 0.9215f),
)
