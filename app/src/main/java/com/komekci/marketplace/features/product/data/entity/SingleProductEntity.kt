package com.komekci.marketplace.features.product.data.entity

import androidx.annotation.Keep
import com.komekci.marketplace.features.product.domain.model.ProductsEntity

@Keep
data class SingleProductEntity(
    val code: String,
    val description: Description,
    val discount: Double?,
    val discountPrice: String? = null,
    val id: Int,
    val images: List<String>,
    val isLiked: Boolean,
    val name: Name,
    val price: Double? = null,
    val roomId: Any,
    val store: ProductStore,
    val brandName: String
) {
    fun toUiEntity(): ProductsEntity {
        return ProductsEntity(
            id = id.toString(),
            name_tm = name.tm,
            price = if (discount!=null && discount > 0) try {
                discountPrice?.toDouble()?:0.0
            } catch (e: Exception) {
                price?:0.0
            } else price?:0.0,
            image = images,
            oldPrice = price?:0.0,
            discount = discount?:0.0,
            discountPrice = discountPrice?:"0.0",
            category_tm = "",
            category_en = "",
            category_ru = "",
            shopName = store.name,
            shopId = store.id,
            isFav = false,
            description_en = description.en,
            description_ru = description.ru,
            description_tm = description.tm,
            name_en = name.en,
            name_ru = name.ru,
            code = code,
            brandName = brandName
        )
    }
}