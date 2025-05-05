package com.komekci.marketplace.features.product.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.auth.data.entity.Message
import com.komekci.marketplace.features.home.data.entity.GetFilter

@Keep
data class FilterState(
    val loading: Boolean = false,
    val error: String? = null,
    val message: List<Message>? = emptyList(),
    val code: Int = 500,
    val data: GetFilter? = null
)
