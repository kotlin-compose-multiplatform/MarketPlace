package com.komekci.marketplace.features.profile.data.entity

import androidx.annotation.Keep
import com.komekci.marketplace.features.auth.data.entity.LocalUserEntity

@Keep
data class User(
    val createdAt: String,
    val email: String,
    val id: Int,
    val image: String?,
    val name: String,
    val phoneNumber: String
) {
    fun toLocalEntity(token: String, store_token: String, isFirstLaunch: String, skipAuth: String): LocalUserEntity {
        return LocalUserEntity(
            id = id.toString(),
            username = name,
            phone = phoneNumber,
            image = image,
            token = token,
            store_token = "",
            email = email,
            isFirstLaunch = isFirstLaunch,
            skipAuth = skipAuth
        )
    }
}