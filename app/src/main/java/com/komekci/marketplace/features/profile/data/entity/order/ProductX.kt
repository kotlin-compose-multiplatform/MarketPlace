package com.komekci.marketplace.features.profile.data.entity.order

import androidx.annotation.Keep

@Keep
data class ProductX(
    val code: String?,
    val discount: Double?,
    val discountPrice: String?,
    val id: Int,
    val price: Double?
)