package com.komekci.marketplace.features.profile.data.entity.order

import androidx.annotation.Keep
import com.komekci.marketplace.features.product.domain.model.ProductsEntity

@Keep
data class Product(
    val code: String,
    val discount: Double?,
    val discountPrice: String?,
    val id: Int,
    val price: Double?
) {
    fun toUiEntity(): ProductsEntity {
        return ProductsEntity(
            id = id.toString(),
            name_tm = "Планшет HONOR Pad X9 4/128GB LTE Gray",
            name_en = "Планшет HONOR Pad X9 4/128GB LTE Gray",
            name_ru = "Планшет HONOR Pad X9 4/128GB LTE Gray",
            price = 6.7,
            image = listOf("https://cdn.ynamdar.com/ynamdar/images/product/TCH1503006P_1.jpg?version=12"),
            oldPrice = 8.9,
            discount = 10.11,
            category_tm = "integer",
            category_en = "consul",
            category_ru = "varius",
            shopName = "Timar",
            isFav = false,
            discountPrice = "0",
            description_en = "",
            description_ru = "",
            description_tm = "",
            brandName = ""
        )
    }
}