package com.komekci.marketplace.features.chat.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.auth.data.entity.Message
import com.komekci.marketplace.features.chat.data.entity.CreateChat

@Keep
data class CreateChatState(
    val loading: Boolean = false,
    val error: String? = null,
    val code: Int = 500,
    val message: List<Message>? = emptyList(),
    val data: CreateChat? = null
)
