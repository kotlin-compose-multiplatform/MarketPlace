package com.komekci.marketplace.features.profile.data.entity.order

import androidx.annotation.Keep

@Keep
data class OrderItemX(
    val count: Int,
    val createdAt: String,
    val id: Int,
    val images: List<ImageX>,
    val product: ProductX
)