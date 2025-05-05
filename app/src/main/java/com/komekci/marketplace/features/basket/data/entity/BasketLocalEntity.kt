package com.komekci.marketplace.features.basket.data.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.komekci.marketplace.features.product.domain.model.ProductsEntity

@Entity
@Keep
data class BasketLocalEntity(
    @PrimaryKey val id: String,
    val count: Int,
    val name_tm: String,
    val name_en: String,
    val name_ru: String,
    val description_tm: String,
    val description_ru: String,
    val description_en: String,
    val price: Double,
    val image: String,
    val oldPrice: Double,
    val discount: Double,
    val discountPrice: String,
    val category_tm: String,
    val category_en: String,
    val category_ru: String,
    val shopName: String,
    val brandName: String,
    val shopId: Int? = null,
    val code: String? = null,
    val isFav: Boolean
) {
    fun toUiEntity() : ProductsEntity {
        return ProductsEntity(
            id = id,
            name_tm = name_tm,
            name_en = name_en,
            name_ru = name_ru,
            description_tm = description_tm,
            description_ru = description_ru,
            description_en = description_en,
            price = price,
            image = listOf(image),
            oldPrice = oldPrice,
            discount = discount,
            discountPrice = discountPrice,
            category_tm = category_tm,
            category_en = category_en,
            category_ru = category_ru,
            shopName = shopName,
            shopId = shopId,
            code = code,
            isFav = isFav,
            brandName = brandName
        )
    }
}

val sampleBasketItem = BasketLocalEntity(
    id = "12345",
    count = 5,
    name_tm = "Sample Product",
    name_en = "Sample Product",
    name_ru = "Пример продукта",
    description_tm = "Обычный текст о товаре",
    description_ru = "Обычное текст о товаре",
    description_en = "Common text about the product",
    price = 19.99,
    image = "path/to/image.jpg",
    oldPrice = 10.99,
    discount = 25.0,
    discountPrice = "24.99",
    category_tm = "Electronics",
    category_en = "Electronics",
    category_ru = "Электроники",
    shopName = "Example Store",
    brandName = "Brand Name",
    shopId = 67890,
    code = "12345",
    isFav = true
)
