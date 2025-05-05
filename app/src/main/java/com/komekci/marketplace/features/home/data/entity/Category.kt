package com.komekci.marketplace.features.home.data.entity

import androidx.annotation.Keep
import com.komekci.marketplace.features.home.domain.model.CategoryEntity

@Keep
data class Category(
    val id: Int,
    val image: String,
    val totalProducts: Int,
    val name: Name
) {
    fun toUIEntity(): CategoryEntity {
        return CategoryEntity(
            title_tm = name.tm,
            title_en = name.en,
            title_ru = name.ru,
            id = id.toString(),
            image = image,
            totalProducts = totalProducts
        )
    }
}