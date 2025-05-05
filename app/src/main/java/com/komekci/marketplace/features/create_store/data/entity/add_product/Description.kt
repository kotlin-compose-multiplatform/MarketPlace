package com.komekci.marketplace.features.create_store.data.entity.add_product

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Description(
    val en: String? = null,
    val ru: String? = null,
    val tm: String? = null
)