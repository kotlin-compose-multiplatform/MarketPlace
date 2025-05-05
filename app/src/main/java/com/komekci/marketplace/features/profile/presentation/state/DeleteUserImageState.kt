package com.komekci.marketplace.features.profile.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.auth.data.entity.Message

@Keep
data class DeleteUserImageState(
    val loading: Boolean = false,
    val message: List<Message>? = emptyList(),
    val error: String? = null,
    val data: String? = null,
    val code: Int = 500
)
