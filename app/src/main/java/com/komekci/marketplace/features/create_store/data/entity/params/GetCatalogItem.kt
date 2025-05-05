package com.komekci.marketplace.features.create_store.data.entity.params

import androidx.annotation.Keep

@Keep
data class GetCatalogItem(
    val categoryId: Int,
    val id: Int,
    val name: Name
)