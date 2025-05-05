package com.komekci.marketplace.features.auth.data.entity

import androidx.annotation.Keep

@Keep
data class SendCodeRequest(
    val userId: Int
)

@Keep
data class SendCodeResponse(
    val success: Boolean
)
