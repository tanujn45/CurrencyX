package dev.tanujn45.currencyx.utils

import kotlin.math.roundToInt

fun roundToTwoDecimalPlaces(value: Double) =
    String.format("%.2f", value).toDouble()

fun roundToTwoDecimalPlaces(value: Float) =
    ((value * 100f).roundToInt() / 100).toFloat()
