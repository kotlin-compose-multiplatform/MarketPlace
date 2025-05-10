package com.komekci.marketplace.features.home.data.entity.country

import androidx.annotation.Keep
import com.komekci.marketplace.features.product.domain.model.ProductsEntity

@Keep
data class Product(
    val code: String? = "",
    val currency: String? = "",
    val description: Description? = Description(),
    val discount: Int? = 0,
    val discountPrice: String? = "",
    val id: Int? = 0,
    val images: List<String>? = listOf(),
    val isBasket: Boolean? = false,
    val isLiked: Boolean? = false,
    val name: Name? = Name(),
    val price: Int? = 0,
    val roomId: Any? = Any(),
    val store: Store? = Store(),
    val storeId: Int? = 0
)

fun Product.toProductsEntity(): ProductsEntity {
    return ProductsEntity(
        id = id.toString(),
        name_tm = name?.tm ?: "",
        name_en = name?.en ?: "",
        name_ru = name?.ru ?: "",
        description_tm = description?.tm ?: "",
        description_ru = description?.ru ?: "",
        description_en = description?.en ?: "",
        price = if(discount!=null && discount>0) discountPrice?.toDoubleOrNull()?:0.0 else price?.toDouble() ?: 0.0,
        image = images,
        oldPrice = price?.toDouble() ?: 0.0, // Adjust logic for oldPrice if needed
        discount = discount?.toDouble() ?: 0.0,
        discountPrice = discountPrice ?: "",
        category_tm = "",
        category_en =  "",
        category_ru =  "",
        shopName = store?.name ?: "",
        shopId = storeId,
        code = code,
        brandName = "",
        isFav = isLiked ?: false
    )
}