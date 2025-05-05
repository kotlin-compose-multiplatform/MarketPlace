package com.komekci.marketplace.features.auth.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.auth.data.entity.Message
import com.komekci.marketplace.features.auth.data.entity.RegisterResponse

@Keep
data class RegisterState(
    val email: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val response: RegisterResponse? = null,
    val message: List<Message>? = emptyList(),
    val error: String? = "",
    val loading: Boolean = false
)
