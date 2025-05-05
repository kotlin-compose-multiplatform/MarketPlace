package com.komekci.marketplace.features.home.presentation.state

import com.komekci.marketplace.features.home.data.entity.country.CountryProductEntity
import com.komekci.marketplace.features.home.data.entity.country.CountryProductEntityItem

data class HomeProductState(
    val data: CountryProductEntity? = null,
    val loading: Boolean = true,
    val error: Boolean = false
)
