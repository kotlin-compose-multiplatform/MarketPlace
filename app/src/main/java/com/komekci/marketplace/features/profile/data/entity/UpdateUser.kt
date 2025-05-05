package com.komekci.marketplace.features.profile.data.entity

import androidx.annotation.Keep
import java.io.File

@Keep
data class UpdateUser(
    val name: String,
    val phone: String,
    val countryId: Int?,
    val regionId: Int?,
    val image: File? = null
)
