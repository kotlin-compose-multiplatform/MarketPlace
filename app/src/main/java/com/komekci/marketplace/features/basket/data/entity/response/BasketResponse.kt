package com.komekci.marketplace.features.basket.data.entity.response

import androidx.annotation.Keep

@Keep
data class BasketResponse(
    val transactionId: String,
    val success: Boolean,
    val url: String,
    val finalUrl: String,
    val checkOrder: String
)