package com.komekci.marketplace.features.basket.data.entity

import androidx.annotation.Keep

@Keep
data class Order(
    val count: Int,
    val productId: Int
)