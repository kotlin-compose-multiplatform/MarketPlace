package com.komekci.marketplace.features.chat.domain.model

import androidx.annotation.Keep

@Keep
data class ChatsModel(
    val id: String,
    val roomId: String,
    val name: String,
    val image: String,
    val lastMessage: String,
    val time: String,
    val isOnline: Boolean,
    val unreadCount: Int
)