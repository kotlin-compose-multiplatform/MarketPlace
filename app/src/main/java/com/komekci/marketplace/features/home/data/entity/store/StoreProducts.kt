package com.komekci.marketplace.features.home.data.entity.store

import androidx.annotation.Keep
import com.komekci.marketplace.features.product.data.entity.Product
import com.komekci.marketplace.features.product.domain.model.CategoryEntity

@Keep
data class StoreProducts(
    val categoryId: Int,
    val id: Int,
    val name: Name,
    val products: List<Product>
) {
    fun toUIEntity():CategoryEntity {
        return CategoryEntity(
            id = id.toString(),
            name_en = name.en,
            name_ru = name.ru,
            name_tm = name.tm,
            products = products.map { it.toUiEntity() }
        )
    }
}