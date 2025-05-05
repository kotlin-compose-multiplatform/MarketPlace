package com.komekci.marketplace.features.chat.data.entity

import androidx.annotation.Keep

@Keep
data class Me(
    val id: Int,
    val image: String,
    val name: String
)