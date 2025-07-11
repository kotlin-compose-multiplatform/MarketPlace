package com.komekci.marketplace.features.chat.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SubscribeChat(
    val room: String
)
