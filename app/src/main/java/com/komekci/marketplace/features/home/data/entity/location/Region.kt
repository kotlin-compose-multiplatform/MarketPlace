package com.komekci.marketplace.features.home.data.entity.location

data class Region(
    val cities: List<CityX>? = listOf(),
    val id: Int? = 0,
    val name: Name? = Name()
)