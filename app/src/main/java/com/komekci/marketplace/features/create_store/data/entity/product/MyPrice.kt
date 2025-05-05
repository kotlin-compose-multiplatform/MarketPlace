package com.komekci.marketplace.features.create_store.data.entity.product

import androidx.annotation.Keep

@Keep
data class MyPrice(
    val currency: String? = null,
    val discount: Double? = null,
    val discountPrice: String? = null,
    val id: Int? = null,
    val price: Double? = null
)