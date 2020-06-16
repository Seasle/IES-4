package com.skyface.utils

import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

fun round(value: Double, precision: Double): Double {
    val multiplier = 10.0.pow(precision)

    return (value * multiplier).roundToInt() / multiplier
}

fun clamp(min: Double, max: Double, value: Double): Double {
    return min.coerceAtLeast(max.coerceAtMost(value))
}

fun powerFormula(value: Double): Double {
    return (value * value.pow(2.0)) * (1 / 10.0.pow(sqrt(value)))
}

fun waterFormula(value: Double): Double {
    return (value * value.pow(3.0)) * (1 / 10.0.pow(sqrt(value)))
}