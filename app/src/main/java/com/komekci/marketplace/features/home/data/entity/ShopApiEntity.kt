package com.komekci.marketplace.features.home.data.entity

import androidx.annotation.Keep
import com.komekci.marketplace.features.home.domain.model.ShopEntity


@Keep
data class StoresApiEntity(
    val stores: List<ShopApiEntity>?,
    val page: Int,
    val size: Int,
    val totalPages: Int
)

@Keep
data class ShopApiEntity(
    val email: Any,
    val id: Int,
    val image: String,
    val instagram: String? = null,
    val tiktok: String? = null,
    val name: String,
    val phoneNumber: String,
    val status: String,
    val totalProducts: String,
    val vip: Any,
    val isLiked: Boolean
) {
    fun toUiEntity(): ShopEntity {
        return ShopEntity(
            name, id.toString(), image, totalProducts,false,
            instagram, tiktok
        )
    }
}
