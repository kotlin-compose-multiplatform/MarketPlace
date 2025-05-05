package com.komekci.marketplace.features.chat.data.entity

import androidx.annotation.Keep
import com.komekci.marketplace.features.chat.domain.model.ChatHistoryModel
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Post(
    val createdAt: String,
    val friendId: Int,
    val friendRole: String?,
    val message: String,
    val postedByUserId: Int,
    val roomId: String,
    val whoPosted: String
) {
    fun toUiEntity(): ChatHistoryModel {
        return ChatHistoryModel(
            userId = postedByUserId.toString(),
            message = message,
            status = "sent",
            date = createdAt,
            time = createdAt,
            whoPosted = whoPosted,
            roomId = roomId.toString(),
            friendId = friendId.toString(),
            friendRole = friendRole?:""
        )
    }
}