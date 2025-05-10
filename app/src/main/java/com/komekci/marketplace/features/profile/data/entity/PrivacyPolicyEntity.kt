package com.komekci.marketplace.features.profile.data.entity


import androidx.annotation.Keep

@Keep
data class PrivacyPolicyEntity(
    val createdAt: String? = null,
    val id: Int? = null,
    val text: String? = null,
    val title: String? = null,
    val updatedAt: String? = null
)