package com.komekci.marketplace.features.home.data.entity.search

import androidx.annotation.Keep
import com.komekci.marketplace.features.product.domain.model.ProductsEntity

@Keep
data class Product(
    val code: String,
    val description: Description,
    val discount: Double?,
    val discountPrice: String,
    val id: Int,
    val images: List<String>,
    val isBasket: Boolean,
    val isLiked: Boolean,
    val name: Name,
    val price: Double,
    val roomId: String,
    val store: Store?
) {
    fun toUiEntity(): ProductsEntity {
        return ProductsEntity(
            id = id.toString(),
            name_tm = name.tm,
            price = if (discount !=null && discount > 0) try {
                discountPrice.toDouble()
            } catch (e: Exception) {
                price
            } else price,
            image = images,
            oldPrice = price,
            discount = discount?:0.0,
            discountPrice = discountPrice,
            category_tm = "",
            category_en = "",
            category_ru = "",
            shopName = store?.name?:"",
            shopId = store?.id?:0,
            isFav = isLiked,
            description_en = description.en,
            description_ru = description.ru,
            description_tm = description.tm,
            name_en = name.en,
            name_ru = name.ru,
            code = code,
            brandName = ""
        )
    }
}