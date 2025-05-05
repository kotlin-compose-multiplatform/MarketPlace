package com.komekci.marketplace.features.create_store.data.entity.add_product

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Price(
    val currency: String? = null,
    val discount: Int? = null,
    val discountPrice: String? = null,
    val price: Int? = null
) {
    fun toProductPrice(): ProductPrice {
        return ProductPrice(
            currency = currency,
            discount = discount?.toDouble(),
            price = price?.toDouble()
        )
    }
}