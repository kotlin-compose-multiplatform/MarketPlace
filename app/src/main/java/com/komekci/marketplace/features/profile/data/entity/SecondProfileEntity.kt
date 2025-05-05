package com.komekci.marketplace.features.profile.data.entity

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SecondProfileEntity(
    val country: Country? = null,
    val createdAt: String? = null,
    val email: String? = null,
    val id: Int? = null,
    val image: String? = null,
    val name: String? = null,
    val phoneNumber: String? = null,
    val region: Region? = null
)