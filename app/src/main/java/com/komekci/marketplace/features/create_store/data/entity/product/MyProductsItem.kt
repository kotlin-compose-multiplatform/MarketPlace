package com.komekci.marketplace.features.create_store.data.entity.product

import androidx.annotation.Keep

@Keep
data class MyProductsItem(
    val brandId: Int? = null,
    val catalogId: Int? = null,
    val categoryId: Int? = null,
    val categoryName: CategoryName? = null,
    val subCatalogName: CategoryName? = null,
    val catalogName: CategoryName? = null,
    val prices: List<MyPrice>? = null,
    val code: String? = null,
    val brandName: String? = null,
    val createdAt: String,
    val currency: String? = null,
    val description: Description,
    val discount: Double? = null,
    val id: Int,
    val images: List<Image>,
    val name: Name,
    val price: Double? = null,
    val status: String,
    val storeId: Int? = null,
    val subCatalogId: Int? = null,
    val updatedAt: String
)