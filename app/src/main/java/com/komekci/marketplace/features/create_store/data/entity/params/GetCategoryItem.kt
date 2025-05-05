package com.komekci.marketplace.features.create_store.data.entity.params

import androidx.annotation.Keep

@Keep
data class GetCategoryItem(
    val createdAt: String,
    val id: Int,
    val image: String,
    val name: Name,
    val updatedAt: String
)