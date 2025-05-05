package com.komekci.marketplace.features.home.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.home.domain.model.ShopEntity

@Keep
data class ShopsState(
    val shops: List<ShopEntity>? = emptyList(),
    val loading: Boolean = true,
    val error: Boolean = false,
    val isEmpty: Boolean = true
)
