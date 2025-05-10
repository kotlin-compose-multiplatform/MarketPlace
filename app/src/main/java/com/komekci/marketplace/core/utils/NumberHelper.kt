package com.komekci.marketplace.core.utils

import kotlin.math.floor

fun main() {
    val t = 10.556667
    println(t.toFixed())
}

fun Double.toFixed(): String {
    return String.format("%.2f", this)
}
