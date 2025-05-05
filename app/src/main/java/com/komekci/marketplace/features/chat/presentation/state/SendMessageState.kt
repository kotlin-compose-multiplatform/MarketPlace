package com.komekci.marketplace.features.chat.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.auth.data.entity.Message
import com.komekci.marketplace.features.chat.data.entity.SendMessage

@Keep
data class SendState(
    val loading: Boolean = false,
    val error: String? = null,
    val code: Int = 500,
    val message: List<Message>? = emptyList(),
    val data: SendMessage? = null
)
