package com.komekci.marketplace.features.profile.data.entity.order

import androidx.annotation.Keep

@Keep
data class StoreOrderRequest(
    /**
     * 'onedayago','oneweekago','onemonthago'
     */
    val date: String? = null,

    /**
     * 'completed', 'cancelled', 'on the way', 'waiting'
     */
    val status: String? = null,
)
