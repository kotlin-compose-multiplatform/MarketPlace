package com.komekci.marketplace.features.create_store.data.entity.store

import androidx.annotation.Keep

@Keep
data class Store(
    val id: Int,
    val image: String,
    val name: String
)