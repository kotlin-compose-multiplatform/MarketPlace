package com.komekci.marketplace.features.auth.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.auth.data.entity.CheckCodeEntity
import com.komekci.marketplace.features.auth.data.entity.Message

@Keep
data class CheckCodeState(
    val loading: Boolean = false,
    val message: List<Message>? = emptyList(),
    val error: String? = null,
    val data: CheckCodeEntity? = null
)
