package com.komekci.marketplace.features.create_store.data.entity.location

import androidx.annotation.Keep

@Keep
data class AddLocationBody(
    val regionId: Int,
    val cityId: Int? = null,
)