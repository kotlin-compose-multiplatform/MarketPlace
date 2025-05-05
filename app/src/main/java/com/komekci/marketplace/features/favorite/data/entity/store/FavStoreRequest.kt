package com.komekci.marketplace.features.favorite.data.entity.store

import androidx.annotation.Keep

@Keep
data class FavStoreRequest(
    val storeId: Int,
    val isLiked: Boolean
)
