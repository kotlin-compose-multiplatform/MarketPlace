package com.komekci.marketplace.features.auth.presentation.state

import com.komekci.marketplace.features.auth.data.entity.country.AllCountryEntityItem

data class CountryState(
    val isLoading: Boolean = false,
    val countries: List<AllCountryEntityItem> = emptyList(),
    val error: String? = null
)
