package com.komekci.marketplace.features.create_store.domain.model

import androidx.annotation.Keep
import com.komekci.marketplace.features.create_store.data.entity.params.GetBrandItem
import com.komekci.marketplace.features.create_store.data.entity.params.GetCatalogItem
import com.komekci.marketplace.features.create_store.data.entity.params.GetCategoryItem
import com.komekci.marketplace.features.create_store.data.entity.params.GetSubCatalogItem

@Keep
data class ProductParams(
    val brands: List<GetBrandItem>? = emptyList(),
    val subCatalogs: List<GetSubCatalogItem>? = emptyList(),
    val categories: List<GetCategoryItem>? = emptyList(),
    val catalogs: List<GetCatalogItem>? = emptyList(),
)
