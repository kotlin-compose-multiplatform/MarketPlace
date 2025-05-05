package com.komekci.marketplace.features.basket.presentation.state

import androidx.annotation.Keep

@Keep
data class BasketPrice(
    val count: Int = 0,
    val total: Double = 0.0,
    val discountPrice: Double = 0.0,
    val deliveryPrice: Double = 0.0,
    val discountPercentage: Double = 0.0,
    val completePrice: Double = 0.0
)
