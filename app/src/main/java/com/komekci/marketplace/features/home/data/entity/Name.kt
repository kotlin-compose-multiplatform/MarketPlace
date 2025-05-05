package com.komekci.marketplace.features.home.data.entity

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class Name(
    val en: String,
    val ru: String,
    val tm: String
)