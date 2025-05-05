package com.komekci.marketplace.features.home.domain.model

import androidx.annotation.Keep

@Keep
data class DiscountEntity(
    val id: String,
    val title_tm: String,
    val title_ru: String,
    val title_en: String,
    val image: String,
    val isFav: Boolean
)
