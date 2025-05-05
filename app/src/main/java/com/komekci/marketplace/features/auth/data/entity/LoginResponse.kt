package com.komekci.marketplace.features.auth.data.entity

import androidx.annotation.Keep

@Keep
data class LoginResponse(
    val message: String,
    val name: String,
    val phoneNumber: String,
    val success: Boolean,
    val userId: Int
)