package com.komekci.marketplace.features.create_store.data.entity.store

import androidx.annotation.Keep

@Keep
data class CreateStoreResponse(
    val store: Store? = null,
    val storeToken: String? = null,
    val success: Boolean = true
)