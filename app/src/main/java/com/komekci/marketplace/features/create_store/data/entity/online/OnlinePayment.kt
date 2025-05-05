package com.komekci.marketplace.features.create_store.data.entity.online

import androidx.annotation.Keep

@Keep
data class OnlinePayment(
    val checkOrder: String? = null,
    val finalUrl: String? = null,
    val success: Boolean? = null,
    val transactionId: String? = null,
    val url: String? = null
)