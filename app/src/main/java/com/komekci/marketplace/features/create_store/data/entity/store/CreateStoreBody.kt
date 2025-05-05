package com.komekci.marketplace.features.create_store.data.entity.store

import androidx.annotation.Keep

@Keep
data class CreateStoreBody(
    val key: String? = null,
    val paymentMethod: String,
    val bank: Int? = null,
    val countryId: Int? = null,
    val month: Int? = null,
)
