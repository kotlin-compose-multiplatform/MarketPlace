package com.komekci.marketplace.features.home.data.entity

import androidx.annotation.Keep

@Keep
data class Catalog(
    val categoryId: Int,
    val id: Int,
    val name: Name
)