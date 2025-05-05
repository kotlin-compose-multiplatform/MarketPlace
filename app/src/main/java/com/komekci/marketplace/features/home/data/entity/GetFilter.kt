package com.komekci.marketplace.features.home.data.entity

import androidx.annotation.Keep

@Keep
data class GetFilter(
    val brand: List<Brand>?,
    val catalog: List<Catalog>?,
    val category: List<CategoryX>?,
    val store: List<ShopApiEntity>?,
    val subCatalog: List<SubCatalog>?
)