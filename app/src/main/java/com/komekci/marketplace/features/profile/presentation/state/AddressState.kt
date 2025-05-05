package com.komekci.marketplace.features.profile.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.profile.data.entity.AddressResponse

@Keep
data class AddressState(
    val loading: Boolean = true,
    val error: String? = null,
    val code: Int? = 500,
    val isEmpty: Boolean = true,
    val data: List<AddressResponse>? = null
)