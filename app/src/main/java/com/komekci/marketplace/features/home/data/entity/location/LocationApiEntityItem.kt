package com.komekci.marketplace.features.home.data.entity.location

import androidx.annotation.Keep
import com.komekci.marketplace.features.home.domain.model.LocationEntity

@Keep
data class LocationApiEntityItem(
    val regions: List<City>,
    val id: Int,
    val name: NameX
) {
    fun toUiEntity(): LocationEntity {
        return LocationEntity(
            id = id.toString(),
            name_tm = name.tm,
            name_en = name.en,
            name_ru = name.ru,
            districts = regions.map { it.toUiEntity() }
        )
    }
}