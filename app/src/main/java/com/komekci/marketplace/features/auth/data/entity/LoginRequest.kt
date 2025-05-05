package com.komekci.marketplace.features.auth.data.entity

import androidx.annotation.Keep

@Keep
data class LoginRequest(
    val phoneNumber: String
)
