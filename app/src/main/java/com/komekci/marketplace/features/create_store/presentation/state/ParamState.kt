package com.komekci.marketplace.features.create_store.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.create_store.domain.model.ProductParams

@Keep
data class ParamState(
    val loading: Boolean = true,
    val error: String? = null,
    val params: ProductParams? = null
)
