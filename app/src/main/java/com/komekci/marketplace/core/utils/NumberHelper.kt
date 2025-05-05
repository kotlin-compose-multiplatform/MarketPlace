package com.komekci.marketplace.core.utils

import kotlin.math.floor

fun main() {
    val t = 10.556667
    println(t.toFixed())
}

fun Double.toFixed(): String {
    val result = (floor((this * 100)) / 100.0).toString()
    return try {
        if(result.split(".")[1].length<2) result.plus("0") else result
    } catch (_: Exception) {
        result
    }
}