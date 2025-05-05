package com.komekci.marketplace.features.create_store.data.entity.store

import androidx.annotation.Keep

@Keep
data class MyStore(
    val categoryId: Int? = null,
    val createdAt: String,
    val email: String? = null,
    val id: Int,
    val image: String? = null,
    val instagram: String? = null,
    val lastPaidId: Any,
    val locations: List<LocationItem>? = null,
    val name: String? = null,
    val phoneNumber: String? = null,
    val status: String,
    val storeLocation: List<StoreLocation>,
    val tiktok: String? = null,
    val updatedAt: String,
    val paymentDeadline: String? = null,
    val deadlineTimestamp: Long? = null,
)