package com.komekci.marketplace.features.profile.data.entity

import androidx.annotation.Keep

@Keep
data class ProfileEntity(
    val success: Boolean,
    val user: User
)