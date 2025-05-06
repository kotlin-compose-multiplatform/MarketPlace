package com.komekci.marketplace.features.product.data.entity

import androidx.annotation.Keep

@Keep
data class ProductRequest(
    val price_lte: Double? = null,
    val price_gte: Double? = null,
    val discount: Boolean? = false,
    val storeId: List<Int> = emptyList(),
    val catalogId: List<String> = emptyList(),
    val brandId: List<String> = emptyList(),
    val categoryId: List<String> = emptyList(),
    val region: String? = null,
    val district: String? = null,
    val size: Int = 20,
    val page: Int = 1,
    val token: String? = null
)