package com.komekci.marketplace.features.create_store.data.entity.add_product

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Size(
    val id: Int? = null,
    val name: String? = null
)