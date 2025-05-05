package com.komekci.marketplace.features.home.data.entity.search

import androidx.annotation.Keep

@Keep
data class SearchApiEntity(
    val page: Int,
    val products: List<Product>,
    val size: Int,
    val totalPages: Int
)