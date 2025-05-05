package com.komekci.marketplace.features.profile.data.entity.order

import androidx.annotation.Keep

@Keep
data class Discount(
    val percent: String?,
    val price: String?
)