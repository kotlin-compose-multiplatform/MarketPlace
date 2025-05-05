package com.komekci.marketplace.features.home.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.home.domain.model.LocationEntity

@Keep
data class LocationStata(
    val locations: List<LocationEntity>? = emptyList(),
    val loading: Boolean = true,
    val error: Boolean = false,
    val isEmpty: Boolean = true
)
