package com.komekci.marketplace.features.auth.data.entity

import androidx.annotation.Keep

@Keep
data class RegisterRequest(
    val name: String,
    val phoneNumber: String,
    val email: String,
    val regionId: Int?,
    val countryId: Int?
)
