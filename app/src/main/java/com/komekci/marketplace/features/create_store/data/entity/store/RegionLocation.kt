package com.komekci.marketplace.features.create_store.data.entity.store

import androidx.annotation.Keep

@Keep
data class RegionLocation(
    val RegionTranslations: List<RegionTranslation>,
    val id: Int
)