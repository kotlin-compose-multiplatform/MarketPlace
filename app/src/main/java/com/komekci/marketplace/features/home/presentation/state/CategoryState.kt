package com.komekci.marketplace.features.home.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.home.domain.model.CategoryEntity

@Keep
data class CategoryState(
    val categories: List<CategoryEntity>? = emptyList(),
    val loading: Boolean = true,
    val error: Boolean = false,
    val isEmpty: Boolean = true
)
