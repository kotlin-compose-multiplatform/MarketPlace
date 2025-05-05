package com.komekci.marketplace.features.create_store.data.entity.add_product

import androidx.annotation.Keep

@Keep
data class UploadProductImage(
    val id: Int,
    val image: String,
    val index: Int? = 0
)