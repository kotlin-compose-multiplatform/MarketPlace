package com.komekci.marketplace.features.create_store.data.entity.payment

import androidx.annotation.Keep

@Keep
data class StorePayment(
    val createdAt: String,
    val end: String,
    val id: Int,
    val key: String,
    val paymentMethod: String,
    val premiumKeyId: Int? = null,
    val start: String,
    val bank: String? = null,
    val transactionId: String? = null,
    val status: String,
    val type: String?,
    val totalPrice: Double?
)