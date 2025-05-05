package com.komekci.marketplace.features.basket.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.auth.data.entity.Message
import com.komekci.marketplace.features.basket.data.entity.response.BasketResponse

@Keep
data class SendBasketRequest(
    val loading: Boolean = false,
    val error: String? = null,
    val message: List<Message>? = emptyList(),
    val code: Int = 500,
    val data: BasketResponse? = null
)
