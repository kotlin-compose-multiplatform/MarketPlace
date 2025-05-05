package com.komekci.marketplace.features.chat.data.entity

import androidx.annotation.Keep

@Keep
data class Details(
    val lastMessage: String?,
    val lastOnline: String?
)