package com.komekci.marketplace.features.chat.data.entity.history

import androidx.annotation.Keep

@Keep
data class Room(
    val createdAt: String,
    val friend: Friend,
    val id: Int,
    val komekchiId: Any,
    val me: Me,
    val roomId: String,
    val updatedAt: String
)