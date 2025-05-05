package com.komekci.marketplace.features.chat.data.entity.unread

import androidx.annotation.Keep

@Keep
data class GetUnreadMessagesItem(
    val createdAt: String,
    val friend: Friend,
    val id: Int,
    val messages: List<Message>,
    val roomId: String
)