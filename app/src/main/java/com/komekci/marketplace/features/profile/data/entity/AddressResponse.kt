package com.komekci.marketplace.features.profile.data.entity

import androidx.annotation.Keep

@Keep
data class AddressResponse(
    val address: String?,
    val createdAt: String,
    val id: Int,
    val title: String?,
    val updatedAt: String,
    val userId: Int
)