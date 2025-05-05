package com.komekci.marketplace.features.home.data.entity.country

import androidx.annotation.Keep
import com.komekci.marketplace.features.home.domain.model.CategoryEntity

@Keep
data class CountryProductEntityItem(
    val id: Int? = 0,
    val totalProducts: Int? = 0,
    val image: String? = "",
    val name: Name? = Name(),
    val products: List<Product>? = listOf()
) {
    fun toCategory(): CategoryEntity {
        return CategoryEntity(
            title_tm = name?.tm?:"",
            title_en = name?.en?:"",
            title_ru = name?.ru?:"",
            id = id.toString(),
            image = image?:"",
            totalProducts = totalProducts?:0
        )
    }
}