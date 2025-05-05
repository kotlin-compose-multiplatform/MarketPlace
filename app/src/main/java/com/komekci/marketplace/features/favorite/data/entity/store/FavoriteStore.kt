package com.komekci.marketplace.features.favorite.data.entity.store

import androidx.annotation.Keep
import com.komekci.marketplace.features.home.domain.model.ShopEntity

@Keep
data class FavoriteStore(
    val email: String,
    val id: Int,
    val image: String,
    val instagram: String,
    val isLiked: Boolean,
    val name: String,
    val phoneNumber: String,
    val status: String,
    val tiktok: String
) {
    fun toUiEntity(): ShopEntity {
        return ShopEntity(
            image = image,
            title = name,
            id = id.toString(),
            isFav = isLiked
        )
    }
}