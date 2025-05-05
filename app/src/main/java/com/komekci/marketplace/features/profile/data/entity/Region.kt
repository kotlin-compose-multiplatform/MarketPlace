package com.komekci.marketplace.features.profile.data.entity

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Region(
    val en: String? = null,
    val id: Int? = null,
    val ru: String? = null,
    val tm: String? = null
)