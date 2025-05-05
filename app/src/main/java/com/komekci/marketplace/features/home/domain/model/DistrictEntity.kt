package com.komekci.marketplace.features.home.domain.model

import androidx.annotation.Keep

@Keep
data class DistrictEntity(
    val id: String,
    val name_tm: String,
    val name_en: String,
    val name_ru: String,
)
