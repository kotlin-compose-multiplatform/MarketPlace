package com.komekci.marketplace.features.auth.data.entity

import androidx.annotation.Keep

@Keep
data class ValidationErrorX(
    val message: Message,
    val `param`: String,
    val type: String
)