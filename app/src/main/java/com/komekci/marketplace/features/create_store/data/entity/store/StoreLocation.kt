package com.komekci.marketplace.features.create_store.data.entity.store

import androidx.annotation.Keep

@Keep
data class StoreLocation(
    val CityLocation: CityLocation,
    val RegionLocation: RegionLocation,
    val cityId: Int,
    val createdAt: String,
    val id: Int,
    val regionId: Int,
    val storeId: Int,
    val updatedAt: String
)