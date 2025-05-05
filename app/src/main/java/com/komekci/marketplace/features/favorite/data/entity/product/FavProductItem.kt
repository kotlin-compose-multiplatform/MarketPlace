package com.komekci.marketplace.features.favorite.data.entity.product

import androidx.annotation.Keep
import com.komekci.marketplace.features.product.domain.model.ProductsEntity

@Keep
data class FavProductItem(
    val brand: String,
    val code: String,
    val createdAt: String,
    val description: Description,
    val discount: Double,
    val id: Int,
    val images: List<String>,
    val isBasket: Boolean,
    val isLiked: Boolean,
    val name: Name,
    val price: Double,
    val storeId: Int,
    val storeName: String
) {
    fun toProductEntity(): ProductsEntity {
        return ProductsEntity(
            id = id.toString(),
            name_tm = name.tm,
            name_en = name.en,
            name_ru = name.ru,
            price = price,
            image = images,
            oldPrice = price,
            discount = discount,
            category_tm = "",
            category_en = "",
            category_ru = "",
            shopName = storeName,
            isFav = isLiked,
            discountPrice = "0",
            description_en = description.en,
            description_ru = description.ru,
            description_tm = description.tm,
            brandName = brand
        )
    }
}