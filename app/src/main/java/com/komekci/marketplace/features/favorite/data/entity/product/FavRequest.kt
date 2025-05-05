package com.komekci.marketplace.features.favorite.data.entity.product

import androidx.annotation.Keep

@Keep
data class FavRequest(
    val productId: Int,
    val isLiked: Boolean
)
