package com.komekci.marketplace.features.create_store.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.create_store.data.entity.add_product.UploadProductImage

@Keep
data class UploadPImageState(
    val loading: Boolean = false,
    val error: String? = null,
    val result: List<UploadProductImage>? = emptyList()
)
