package com.komekci.marketplace.features.home.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.home.domain.model.DiscountEntity

@Keep
data class DiscountState(
    val discounts: List<DiscountEntity>? = emptyList(),
    val loading: Boolean = true,
    val error: Boolean = false,
    val isEmpty: Boolean = true
)