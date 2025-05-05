package com.komekci.marketplace.features.home.data.entity.location

import androidx.annotation.Keep
import com.komekci.marketplace.features.home.domain.model.DistrictEntity

@Keep
data class City(
    val id: Int,
    val name: NameX
) {
    fun toUiEntity(): DistrictEntity {
        return DistrictEntity(
            id = id.toString(), name_tm = name.tm, name_en = name.en, name_ru = name.ru
        )
    }
}