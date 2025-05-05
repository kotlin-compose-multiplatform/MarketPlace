package com.komekci.marketplace.features.product.domain.model

import androidx.annotation.Keep

@Keep
data class StoreProductRequest(
    val token: String,
    val storeId: String,
    val limit: Int = 20,
    val region: String? = null,
    val district: String? = null,
)
