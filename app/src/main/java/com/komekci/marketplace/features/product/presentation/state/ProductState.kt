package com.komekci.marketplace.features.product.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.product.domain.model.ProductsEntity

@Keep
data class ProductState(
    val loading: Boolean = false,
    val error: String? = null,
    val products: List<ProductsEntity>? = emptyList(),
    val code: Int = 500,
    val isEmpty: Boolean = products.isNullOrEmpty(),
    val hasMore: Boolean = true
)
