package com.komekci.marketplace.features.create_store.data.entity.add_product

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SingleProduct(
    val ages: List<Age?>? = null,
    val brandId: Int? = null,
    val catalogId: Int? = null,
    val categoryId: Int? = null,
    val categoryName: CategoryName? = null,
    val subCatalogName: CategoryName? = null,
    val catalogName: CategoryName? = null,
    val code: String? = null,
    val brandName: String? = null,
    val count: Int? = null,
    val createdAt: String? = null,
    val currency: String? = null,
    val description: Description? = null,
    val discount: Int? = null,
    val discountPrice: String? = null,
    val id: Int? = null,
    val images: List<Image?>? = null,
    val name: Name? = null,
    val price: Int? = null,
    val prices: List<Price?>? = null,
    val sizes: List<Size?>? = null,
    val status: String? = null,
    val storeId: Int? = null,
    val subCatalogId: Int? = null,
    val updatedAt: String? = null,

)