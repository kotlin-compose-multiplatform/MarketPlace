package com.komekci.marketplace.features.home.data.entity.location

data class CountryLocationEntityItem(
    val id: Int? = 0,
    val name: Name? = Name(),
    val regions: List<Region>? = listOf()
)