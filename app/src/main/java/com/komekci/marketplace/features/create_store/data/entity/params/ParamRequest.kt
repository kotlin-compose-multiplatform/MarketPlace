package com.komekci.marketplace.features.create_store.data.entity.params

import androidx.annotation.Keep

@Keep
data class ParamRequest(
    val categoryId: Int? = null,
    val catalogId: Int? = null
)
