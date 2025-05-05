package com.komekci.marketplace.features.create_store.data.entity.add_product

import androidx.annotation.Keep

@Keep
data class AddProductRequest(
    val brandId: String,
    val catalogId: String,
    val categoryId: String,
    val code: String,
    val discount: String,
    val imagesId: List<Int>,
    val prices: List<ProductPrice>,
    val subCatalogId: String,
    val translations: Translations
)