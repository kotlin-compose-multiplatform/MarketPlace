package com.komekci.marketplace.features.favorite.data.entity.store

import androidx.annotation.Keep
import com.komekci.marketplace.features.favorite.data.entity.product.FavProductItem

@Keep
data class FavoriteProductRoot(
    val products: List<FavProductItem>?,
    val page: Int,
    val size: Int,
    val totalPages: Int
)
