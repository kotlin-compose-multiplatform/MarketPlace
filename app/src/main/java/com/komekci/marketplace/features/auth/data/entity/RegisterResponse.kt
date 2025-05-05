package com.komekci.marketplace.features.auth.data.entity

import androidx.annotation.Keep

@Keep
data class RegisterResponse(
    val name: String,
    val phoneNumber: String,
    val success: Boolean,
    val userId: Int
)