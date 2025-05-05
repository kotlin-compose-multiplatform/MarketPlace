package com.komekci.marketplace.features.auth.data.entity.country

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class Name(
    val en: String? = null,
    val ru: String? = null,
    val tm: String? = null
)