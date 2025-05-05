package com.komekci.marketplace.features.profile.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.auth.data.entity.Message
import com.komekci.marketplace.features.profile.data.entity.payments.PaymentHistory

@Keep
data class PayWithKeyState(
    val loading: Boolean = false,
    val message: List<Message>? = emptyList(),
    val error: String? = null,
    val data: PaymentHistory? = null,
    val code: Int = 500
)
