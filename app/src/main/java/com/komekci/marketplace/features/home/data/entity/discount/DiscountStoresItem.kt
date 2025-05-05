package com.komekci.marketplace.features.home.data.entity.discount

import androidx.annotation.Keep
import com.komekci.marketplace.features.home.domain.model.DiscountEntity

@Keep
data class DiscountStoresItem(
    val id: Int,
    val image: String,
    val max_discount: Int,
    val name: String
) {
    fun toUiEntity(): DiscountEntity {
        return DiscountEntity(
            id = id.toString(),
            title_tm = name,
            title_ru = name,
            title_en = name,
            image = image,
            isFav = false

        )
    }
}