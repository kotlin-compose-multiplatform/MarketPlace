package com.komekci.marketplace.features.product.domain.model

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.navigation.NavType
import com.komekci.marketplace.features.basket.data.entity.BasketLocalEntity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


object DoubleNavType : NavType<Double>(isNullableAllowed = true) {
    override fun get(bundle: Bundle, key: String): Double? {
        return bundle.getDouble(key)
    }

    override fun parseValue(value: String): Double {
        return value.toDouble()
    }

    override fun put(bundle: Bundle, key: String, value: Double) {
        bundle.putDouble(key, value)
    }
}

@Serializable
@Parcelize
@Keep
data class ProductsEntity(
    val id: String,
    val name_tm: String,
    val name_en: String,
    val name_ru: String,
    val description_tm: String,
    val description_ru: String,
    val description_en: String,
    val price: Double,
    val image: List<String>?,
    val oldPrice: Double,
    val discount: Double,
    val discountPrice: String,
    val category_tm: String,
    val category_en: String,
    val category_ru: String,
    val shopName: String,
    val shopId: Int? = null,
    val code: String? = null,
    val brandName: String,
    val isFav: Boolean
) : Parcelable {
    fun toBasketEntity(): BasketLocalEntity {
        return BasketLocalEntity(
            id = id,
            count = 1,
            name_tm = name_tm,
            name_en = name_en,
            name_ru = name_ru,
            description_tm = description_tm,
            description_ru = description_ru,
            description_en = description_en,
            price = price,
            image = if (image.isNullOrEmpty().not()) image?.get(0) ?: "" else "",
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

val sampleProductWithoutDiscount = ProductsEntity(
    id = "12345",
    name_tm = "Mysal Önümi",
    name_en = "Sample Product",
    name_ru = "Пример Товара",
    description_tm = "Bu önümiň aýratynlyklary we artykmaçlyklary.",
    description_en = "Features and benefits of this product.",
    description_ru = "Особенности и преимущества этого продукта.",
    price = 99.99,
    image = listOf(
        "https://images.pexels.com/photos/4158/apple-iphone-smartphone-desk.jpg?cs=srgb&dl=pexels-pixabay-4158.jpg&fm=jpg&_gl=1*yvfyk7*_ga*MTc0MjkwMDg2Ny4xNzMyMDkyNzQw*_ga_8JE65Q40S6*MTczMjA5MjczOS4xLjEuMTczMjA5Mjc1OC4wLjAuMA..",
        "https://images.pexels.com/photos/4158/apple-iphone-smartphone-desk.jpg?cs=srgb&dl=pexels-pixabay-4158.jpg&fm=jpg&_gl=1*yvfyk7*_ga*MTc0MjkwMDg2Ny4xNzMyMDkyNzQw*_ga_8JE65Q40S6*MTczMjA5MjczOS4xLjEuMTczMjA5Mjc1OC4wLjAuMA.."
    ),
    oldPrice = 99.99,
    discount = 0.0,
    discountPrice = "80.00",
    category_tm = "Elektronika",
    category_en = "Electronics",
    category_ru = "Электроника",
    shopName = "Komekchi TM Shop",
    shopId = 1,
    code = "ABC123",
    brandName = "BestBrand",
    isFav = true
)


val sampleProduct = ProductsEntity(
    id = "12345",
    name_tm = "Mysal Önümi",
    name_en = "Sample Product",
    name_ru = "Пример Товара",
    description_tm = "Bu önümiň aýratynlyklary we artykmaçlyklary.",
    description_en = "Features and benefits of this product.",
    description_ru = "Особенности и преимущества этого продукта.",
    price = 99.99,
    image = listOf(
        "https://images.pexels.com/photos/4158/apple-iphone-smartphone-desk.jpg?cs=srgb&dl=pexels-pixabay-4158.jpg&fm=jpg&_gl=1*yvfyk7*_ga*MTc0MjkwMDg2Ny4xNzMyMDkyNzQw*_ga_8JE65Q40S6*MTczMjA5MjczOS4xLjEuMTczMjA5Mjc1OC4wLjAuMA..",
        "https://images.pexels.com/photos/4158/apple-iphone-smartphone-desk.jpg?cs=srgb&dl=pexels-pixabay-4158.jpg&fm=jpg&_gl=1*yvfyk7*_ga*MTc0MjkwMDg2Ny4xNzMyMDkyNzQw*_ga_8JE65Q40S6*MTczMjA5MjczOS4xLjEuMTczMjA5Mjc1OC4wLjAuMA.."
    ),
    oldPrice = 120.00,
    discount = 20.0,
    discountPrice = "80.00",
    category_tm = "Elektronika",
    category_en = "Electronics",
    category_ru = "Электроника",
    shopName = "Komekchi TM Shop",
    shopId = 1,
    code = "ABC123",
    brandName = "BestBrand",
    isFav = true
)
