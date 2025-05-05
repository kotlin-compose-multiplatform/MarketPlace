package com.komekci.marketplace.features.auth.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.auth.data.entity.Message

@Keep
data class SendCodeState(
    val loading: Boolean = false,
    val error: String? = null,
    val message: List<Message>? = emptyList(),
    val success: Boolean = false
)
