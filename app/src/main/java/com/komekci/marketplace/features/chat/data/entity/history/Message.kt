package com.komekci.marketplace.features.chat.data.entity.history

import androidx.annotation.Keep

@Keep
data class Message(
    val createdAt: String,
    val id: Int,
    val isRead: Boolean,
    val message: String?,
    val postedByUserId: Int,
    val whoPosted: String
)