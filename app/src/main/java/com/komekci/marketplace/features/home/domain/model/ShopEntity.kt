package com.komekci.marketplace.features.home.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ShopEntity(
    val title: String = "Title",
    val id: String = "1",
    val image: String = "",
    val totalProducts: String = "0",
    val isFav: Boolean = false,
    val instagram: String? = null,
    val tiktok: String? = null,

)
