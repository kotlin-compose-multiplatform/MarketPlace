package com.komekci.marketplace.features.create_store.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class UpdateLocationNavParams(
    val addressId: String,
    val regionId: String,
    val cityId: String,
)
