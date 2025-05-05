package com.komekci.marketplace.features.auth.data.entity

import androidx.annotation.Keep

@Keep
data class ApiError(
    val message: String,
    val path: String,
    val success: Boolean,
    val timestamps: Long,
    val validationErrors: List<ValidationErrorX>
)