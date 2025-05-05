package com.komekci.marketplace.features.chat.data.entity

import androidx.annotation.Keep

@Keep
data class SendMessage(
    val post: Post,
    val success: Boolean
)

@Keep
data class SendMessageRequest(
    val message: String
)