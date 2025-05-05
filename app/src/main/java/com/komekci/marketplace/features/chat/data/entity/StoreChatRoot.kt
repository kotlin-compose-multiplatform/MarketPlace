package com.komekci.marketplace.features.chat.data.entity

import androidx.annotation.Keep

@Keep
data class StoreChatRoot(
    val users: List<Store>? = null
)
