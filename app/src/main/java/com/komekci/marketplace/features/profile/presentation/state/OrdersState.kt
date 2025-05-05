package com.komekci.marketplace.features.profile.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.profile.data.entity.order.OrderResponseItem

@Keep
data class OrdersState(
    val loading: Boolean = true,
    val error: String? = null,
    val code: Int = 500,
    val isEmpty: Boolean = true,
    val data: List<OrderResponseItem>? = null,
    val selectedOrder: OrderResponseItem? = null
)