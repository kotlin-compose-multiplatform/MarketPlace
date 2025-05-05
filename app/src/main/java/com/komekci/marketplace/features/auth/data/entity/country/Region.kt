package com.komekci.marketplace.features.auth.data.entity.country

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class Region(
    val id: Int? = 0,
    val name: Name? = Name()
)