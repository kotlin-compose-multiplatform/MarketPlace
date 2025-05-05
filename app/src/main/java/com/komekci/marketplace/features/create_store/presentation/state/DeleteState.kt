package com.komekci.marketplace.features.create_store.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.auth.data.entity.Message

@Keep
data class DeleteState(
    val loading: Boolean = false,
    val error: String? = null,
    val message: List<Message>? = emptyList(),
    val code: Int = 500,
)
