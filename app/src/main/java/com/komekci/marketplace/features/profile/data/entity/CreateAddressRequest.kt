package com.komekci.marketplace.features.profile.data.entity

import androidx.annotation.Keep

@Keep
data class CreateAddressRequest(
    val address: String,
    val title: String
)