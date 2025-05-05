package com.komekci.marketplace.features.product.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.product.domain.model.ProductsEntity
import com.komekci.marketplace.features.product.domain.model.sampleProduct
import com.komekci.marketplace.features.product.domain.model.sampleProductWithoutDiscount

@Keep
data class SingleProductState(
    val loading: Boolean = false,
    val error: String? = null,
    val products: ProductsEntity? = null
)
