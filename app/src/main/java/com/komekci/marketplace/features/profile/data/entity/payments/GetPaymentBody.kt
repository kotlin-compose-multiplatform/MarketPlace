package com.komekci.marketplace.features.profile.data.entity.payments

import androidx.annotation.Keep

@Keep
data class GetPaymentBody(
    val date: String? = null,
    val history: String? = null,
)
