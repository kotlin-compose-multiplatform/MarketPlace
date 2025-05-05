package com.komekci.marketplace.features.create_store.data.entity.store

import androidx.annotation.Keep

@Keep
data class CityLocation(
    val CityTranslations: List<CityTranslation>,
    val id: Int
)