package com.komekci.marketplace.features.product.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.product.domain.model.CategoryEntity

@Keep
data class CategoryState(
    val categories: List<CategoryEntity>? = emptyList(),
    val loading: Boolean = true,
    val error: Boolean = false
)