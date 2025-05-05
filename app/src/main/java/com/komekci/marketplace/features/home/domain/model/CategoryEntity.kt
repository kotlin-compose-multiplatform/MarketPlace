package com.komekci.marketplace.features.home.domain.model

import androidx.annotation.Keep

@Keep
data class CategoryEntity(
    val title_tm: String,
    val title_en: String,
    val title_ru: String,
    val id: String,
    val image: String,
    val totalProducts: Int,
)

val testCategoryEntity = CategoryEntity(
    title_tm = "title_tm",
    title_en = "title_en",
    title_ru = "title_ru",
    id = "id",
    image = "image",
    totalProducts = 0
)
