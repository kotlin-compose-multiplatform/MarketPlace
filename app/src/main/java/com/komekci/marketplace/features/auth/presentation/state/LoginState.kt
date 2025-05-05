package com.komekci.marketplace.features.auth.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.auth.data.entity.LoginResponse
import com.komekci.marketplace.features.auth.data.entity.Message

@Keep
data class LoginState(
    val loading: Boolean = false,
    val message: List<Message>? = emptyList(),
    val error: String? = null,
    val data: LoginResponse? = null
)
