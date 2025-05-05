package com.komekci.marketplace.features.auth.data.entity

import androidx.annotation.Keep

@Keep
data class LocalUserEntity(
    val id: String?,
    val username: String?,
    val phone: String?,
    val image: String?,
    val token: String?,
    val store_token: String?,
    val email: String?,
    val skipAuth: String,
    val isFirstLaunch: String,
    val countryId: Int? = null,
    val regionId: Int? = null,
)
