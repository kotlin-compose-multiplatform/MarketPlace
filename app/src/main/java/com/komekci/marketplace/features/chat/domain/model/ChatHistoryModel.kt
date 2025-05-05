package com.komekci.marketplace.features.chat.domain.model

import androidx.annotation.Keep

@Keep
data class ChatHistoryModel(
    val userId: String,
    val message: String,
    val status: String,
    val date: String,
    val time: String,
    val whoPosted: String = "user",
    val friendId: String = "",
    val roomId: String = "",
    val friendRole: String = ""
)