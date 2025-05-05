package com.komekci.marketplace.features.profile.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.auth.data.entity.Message
import com.komekci.marketplace.features.profile.data.entity.UpdateUserResponse

@Keep
data class UpdateUserState(
    val loading: Boolean = false,
    val message: List<Message>? = emptyList(),
    val error: String? = null,
    val data: UpdateUserResponse? = null
)
