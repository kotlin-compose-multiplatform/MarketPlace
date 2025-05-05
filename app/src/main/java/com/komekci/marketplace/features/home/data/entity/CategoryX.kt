package com.komekci.marketplace.features.home.data.entity

import androidx.annotation.Keep

@Keep
data class CategoryX(
    val createdAt: String,
    val id: Int,
    val image: String,
    val name: Name,
    val updatedAt: String
)