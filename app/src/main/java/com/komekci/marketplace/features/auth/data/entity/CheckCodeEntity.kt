package com.komekci.marketplace.features.auth.data.entity

import androidx.annotation.Keep

@Keep
data class CheckCodeEntity(
    val isStoreExist: Boolean,
    val komekchi: UserKomekchi?,
    val message: Message?,
    val store: UserStore?,
    val success: Boolean,
    val user: User
)

@Keep
data class UserKomekchi(
    val paymentDeadline: String
)

@Keep
data class CheckCodeRequest(
    val userId: Int,
    val code: String
)