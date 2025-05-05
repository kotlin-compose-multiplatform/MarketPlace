package com.komekci.marketplace.features.profile.data.entity

import androidx.annotation.Keep

@Keep
data class UpdateUserResponse(
    val success: Boolean,
    val user: UserX
)