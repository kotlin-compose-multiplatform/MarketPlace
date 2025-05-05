package com.komekci.marketplace.features.store_chat.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.chat.data.entity.komekchi.KomekchiUserSearch

@Keep
data class KomekchiListState(
    val loading: Boolean = true,
    val error: String? = null,
    val list: List<KomekchiUserSearch>? = null
)
