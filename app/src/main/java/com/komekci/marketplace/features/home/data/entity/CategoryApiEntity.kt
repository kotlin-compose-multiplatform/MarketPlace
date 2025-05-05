package com.komekci.marketplace.features.home.data.entity

import androidx.annotation.Keep

@Keep
data class CategoryApiEntity(
    val category: List<Category>,
    val success: Boolean
)