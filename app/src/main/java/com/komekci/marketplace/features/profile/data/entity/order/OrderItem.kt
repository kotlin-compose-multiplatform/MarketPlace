package com.komekci.marketplace.features.profile.data.entity.order

import androidx.annotation.Keep

@Keep
data class OrderItem(
    val count: Int,
    val id: Int,
    val images: List<Image>,
    val product: Product?
)