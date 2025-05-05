package com.komekci.marketplace.features.create_store.data.entity.add_product

import androidx.annotation.Keep

@Keep
data class ProductPrice(
    val currency: String? = null,
    val discount: Double? = null,
    val price: Double? = null
)