package com.komekci.marketplace.features.chat.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.chat.domain.model.ChatHistoryModel

@Keep
data class ChatHistoryState(
    val chats: List<ChatHistoryModel>? = emptyList(),
    val loading: Boolean = true,
    val error: Boolean = false
)
