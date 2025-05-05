package com.komekci.marketplace.features.home.domain.model

import androidx.annotation.Keep

@Keep
data class SearchRequest(
    val language: String,
    val token: String,
    val product: String,
    val category: String,
    val store: String
)
