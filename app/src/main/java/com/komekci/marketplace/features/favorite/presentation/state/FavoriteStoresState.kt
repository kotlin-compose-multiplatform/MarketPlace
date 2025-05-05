package com.komekci.marketplace.features.favorite.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.auth.data.entity.Message
import com.komekci.marketplace.features.favorite.data.entity.store.FavoriteStore

@Keep
data class FavoriteStoresState(
    val loading: Boolean = true,
    val error: String? = null,
    val message: List<Message>? = emptyList(),
    val code: Int = 500,
    val data: List<FavoriteStore>? = emptyList()
)
