package com.komekci.marketplace.features.chat.data.entity

import androidx.annotation.Keep

@Keep
data class CreateChat(
    val createdAt: String,
    val id: Int,
    val komekchiId: Int? = null,
    val roomId: String,
    val storeId: Int,
    val updatedAt: String,
    val userId: Int? = null
)

@Keep
data class CreateChatRequest(
    val storeId: Int
)

@Keep
data class CreateKomekchiChatRequest(
    val komekchiId: Int
)