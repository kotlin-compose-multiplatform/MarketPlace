package com.komekci.marketplace.features.chat.data.entity.unread

import androidx.annotation.Keep

@Keep
data class Message(
    val createdAt: String,
    val id: Int,
    val isRead: Boolean,
    val message: String,
    val postedByUserId: Int,
    val roomId: Int,
    val updatedAt: String,
    val whoPosted: String
)