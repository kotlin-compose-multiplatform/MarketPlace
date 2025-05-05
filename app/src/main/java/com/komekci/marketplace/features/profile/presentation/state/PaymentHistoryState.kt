package com.komekci.marketplace.features.profile.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.profile.data.entity.payments.Payment

@Keep
data class PaymentHistoryState(
    val loading: Boolean = true,
    val error: String? = null,
    val code: Int = 500,
    val isEmpty: Boolean = true,
    val data: List<Payment>? = null,
)