package com.komekci.marketplace.features.home.data.entity

import androidx.annotation.Keep

@Keep
data class FilterRequest(
    val categoryId: List<String>? = null,
    val catalogId: List<String>? = null
)
