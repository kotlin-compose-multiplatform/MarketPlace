package com.komekci.marketplace.features.chat.data.entity.komekchi

import android.os.SystemClock
import androidx.annotation.Keep
import com.komekci.marketplace.features.chat.domain.model.ChatsModel
import java.time.LocalDateTime

@Keep
data class KomekchiUserSearch(
    val createdAt: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val phoneNumber: String? = null
) {
    fun toChatModel(): ChatsModel {
        return ChatsModel(
            id = id.toString(),
            roomId = "",
            name = name+"",
            image = "",
            lastMessage = phoneNumber.toString(),
            time = "",
            isOnline = true,
            unreadCount = 0
        )
    }
}