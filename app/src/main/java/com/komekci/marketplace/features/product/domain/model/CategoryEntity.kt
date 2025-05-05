package com.komekci.marketplace.features.product.domain.model

import androidx.annotation.Keep

@Keep
data class CategoryEntity(
    val id: String,
    val name_tm: String,
    val name_ru: String,
    val name_en: String,
    val products: List<ProductsEntity>
)