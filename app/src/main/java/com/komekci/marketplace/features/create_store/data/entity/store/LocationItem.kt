package com.komekci.marketplace.features.create_store.data.entity.store

import androidx.annotation.Keep

@Keep
data class LocationItem(
    val city: City? = null,
    val id: Int,
    val region: Region? = null
)