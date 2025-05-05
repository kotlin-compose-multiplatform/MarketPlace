package com.komekci.marketplace.features.home.data.entity.country

import androidx.annotation.Keep
import com.komekci.marketplace.features.home.data.entity.ShopApiEntity

@Keep
data class CountryProductEntity(
    val products: List<CountryProductEntityItem>? = null,
    val stores: List<ShopApiEntity>? = null
)