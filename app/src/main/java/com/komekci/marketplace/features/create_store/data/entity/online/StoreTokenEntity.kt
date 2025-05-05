package com.komekci.marketplace.features.create_store.data.entity.online

import androidx.annotation.Keep

@Keep
data class StoreTokenEntity(
    val paymentDeadline: String? = null,
    val store: Store? = null,
    val storeToken: String? = null,
    val success: Boolean? = null
)