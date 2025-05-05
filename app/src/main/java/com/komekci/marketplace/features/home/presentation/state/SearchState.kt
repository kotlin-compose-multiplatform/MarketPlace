package com.komekci.marketplace.features.home.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.product.domain.model.ProductsEntity

@Keep
data class SearchState(
    val loading: Boolean = false,
    val error: String? = null,
    val products: List<ProductsEntity>? = emptyList()
)
