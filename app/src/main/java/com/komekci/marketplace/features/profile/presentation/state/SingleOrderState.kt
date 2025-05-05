package com.komekci.marketplace.features.profile.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.profile.data.entity.order.SingleOrder

@Keep
data class SingleOrderState(
    val loading: Boolean = true,
    val error:String? = null,
    val data: SingleOrder? = null
)
