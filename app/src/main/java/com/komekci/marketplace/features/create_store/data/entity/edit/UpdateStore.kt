package com.komekci.marketplace.features.create_store.data.entity.edit

import androidx.annotation.Keep
import java.io.File


@Keep
data class UpdateStore(
    val name: String,
    val phone: String,
    val instagram: String,
    val tiktok: String,
    val image: File? = null
)

@Keep
data class UpdateStoreBody(
    val name: String,
    val phoneNumber: String,
    val instagram: String,
    val tiktok: String,
)