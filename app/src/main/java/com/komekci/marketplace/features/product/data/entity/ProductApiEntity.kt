package com.komekci.marketplace.features.product.data.entity

import androidx.annotation.Keep

@Keep
data class ProductApiEntity(
    val page: Int,
    val products: List<Product>,
    val size: Int,
    val totalPages: Int
)