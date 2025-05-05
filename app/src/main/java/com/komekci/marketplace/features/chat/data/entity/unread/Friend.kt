package com.komekci.marketplace.features.chat.data.entity.unread

import androidx.annotation.Keep

@Keep
data class Friend(
    val id: Int,
    val image: String,
    val name: String
)