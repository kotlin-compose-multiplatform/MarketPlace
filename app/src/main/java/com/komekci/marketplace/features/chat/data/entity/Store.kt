package com.komekci.marketplace.features.chat.data.entity

import androidx.annotation.Keep
import com.komekci.marketplace.features.chat.domain.model.ChatsModel

@Keep
data class Store(
    val createdAt: String,
    val friend: Friend,
    val id: Int,
    val me: Me,
    val roomId: String,
    val unReadMessagesCount: String
) {
    fun toUiEntity(): ChatsModel {
        return ChatsModel(
            id = id.toString(),
            roomId = roomId,
            name = friend.name,
            image = friend.image,
            lastMessage = friend.details.lastMessage ?: "",
            time = friend.details.lastOnline ?: createdAt,
            isOnline = false,
            unreadCount = try {
                unReadMessagesCount.toInt()
            } catch (_: Exception) {
                0
            }
        )
    }
}