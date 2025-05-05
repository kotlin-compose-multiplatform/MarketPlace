package com.komekci.marketplace.features.favorite.data.entity.store

import androidx.annotation.Keep

@Keep
data class FavoriteStoreRoot(
    val stores: List<FavoriteStore>?,
    val page: Int,
    val size: Int,
    val totalPages: Int
)
