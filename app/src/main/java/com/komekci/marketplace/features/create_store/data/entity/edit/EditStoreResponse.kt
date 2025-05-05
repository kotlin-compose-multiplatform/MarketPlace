package com.komekci.marketplace.features.create_store.data.entity.edit

import androidx.annotation.Keep

@Keep
data class EditStoreResponse(
    val image: String? = null,
    val name: String? = null,
    val phoneNumber: String? = null
)
