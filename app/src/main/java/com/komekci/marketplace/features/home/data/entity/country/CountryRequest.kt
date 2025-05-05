package com.komekci.marketplace.features.home.data.entity.country

import androidx.annotation.Keep

@Keep
data class CountryRequest(
    val countryId: String = "",
    val regionId: String = "",
)
