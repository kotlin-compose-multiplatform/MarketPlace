package com.komekci.marketplace.features.create_store.data.entity.add_product

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Image(
    val id: Int? = null,
    val image: String? = null
)