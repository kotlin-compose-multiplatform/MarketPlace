package com.komekci.marketplace.state

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import com.komekci.marketplace.features.product.data.entity.ProductRequest

val LocalProductFilter = compositionLocalOf {
    mutableStateOf(ProductRequest())
}