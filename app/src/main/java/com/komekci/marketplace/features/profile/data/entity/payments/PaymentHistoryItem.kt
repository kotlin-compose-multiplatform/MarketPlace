package com.komekci.marketplace.features.profile.data.entity.payments

import androidx.annotation.Keep

@Keep
data class Payment(
    val createdAt: String,
    val end: String,
    val id: Int,
    val key: String,
    val paymentMethod: String,
    val premiumKeyId: Int,
    val price: Double,
    val start: String,
    val status: String,
    val type: String
)