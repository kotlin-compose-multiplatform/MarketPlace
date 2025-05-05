package com.komekci.marketplace.features.chat.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.chat.domain.model.ChatsModel

@Keep
data class ChatsState(
    val chats: List<ChatsModel>? = emptyList(),
    val loading: Boolean = true,
    val error: Boolean = false
)
