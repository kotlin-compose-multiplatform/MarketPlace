package com.komekci.marketplace.features.basket.data.entity

import androidx.annotation.Keep

@Keep
data class Basket(
    val orders: List<Order>,
    val storeId: Int
)