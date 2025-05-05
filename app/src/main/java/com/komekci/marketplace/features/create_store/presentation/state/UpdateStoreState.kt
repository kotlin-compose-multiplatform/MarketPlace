package com.komekci.marketplace.features.create_store.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.auth.data.entity.Message
import com.komekci.marketplace.features.create_store.data.entity.edit.EditStoreResponse

@Keep
data class UpdateStoreState(
    val loading: Boolean = false,
    val error: String? = null,
    val code: Int = 500,
    val message: List<Message>? = emptyList(),
    val data: EditStoreResponse? = null
)
