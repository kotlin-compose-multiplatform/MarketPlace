package com.komekci.marketplace.features.chat.data.entity.history

import androidx.annotation.Keep
import com.komekci.marketplace.features.chat.domain.model.ChatHistoryModel

@Keep
data class GetRoomMessages(
    val messages: List<Message>,
    val room: Room
) {
    fun toUiEntity(): List<ChatHistoryModel> {
        return messages.map { msg->
            ChatHistoryModel(
                userId = msg.postedByUserId.toString(),
                message = msg.message?:"",
                status = "sent",
                date = msg.createdAt,
                time = msg.createdAt,
                whoPosted = msg.whoPosted,
                roomId = room.roomId,
                friendId = room.friend.id.toString(),
                friendRole = ""
            )
        }
    }
}