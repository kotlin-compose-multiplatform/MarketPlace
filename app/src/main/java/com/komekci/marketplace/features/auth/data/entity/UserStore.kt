package com.komekci.marketplace.features.auth.data.entity

import androidx.annotation.Keep

@Keep
data class UserStore(
    val image: String,
    val name: String,
    val phoneNumber: String,
    val token: String
)