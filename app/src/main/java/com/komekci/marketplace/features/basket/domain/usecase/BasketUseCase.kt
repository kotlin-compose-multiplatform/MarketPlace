package com.komekci.marketplace.features.basket.domain.usecase

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.basket.data.entity.Basket
import com.komekci.marketplace.features.basket.data.entity.BasketLocalEntity
import com.komekci.marketplace.features.basket.data.entity.BasketRequest
import com.komekci.marketplace.features.basket.data.entity.Order
import com.komekci.marketplace.features.basket.data.entity.response.BasketResponse
import com.komekci.marketplace.features.basket.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow

class BasketUseCase(
    private val repository: BasketRepository
) {
    suspend fun sendOrder(
        token: String,
        products: List<BasketLocalEntity>,
        addressId: String,
        addressName: String,
        addressValue: String,
        addressPhone: String,
        guestId: String,
        isAuth: Boolean,
        bank: Int,
        paymentMethod: String
    ): Flow<Resource<BasketResponse>> {
//        val basket: List<Basket> = products.map {
//            val sid = it.shopId ?: 0
//            Basket(
//                storeId = sid,
//                orders = products.filter { product -> product.shopId == sid }
//                    .map { p2 -> Order(p2.count, p2.id.toInt()) }
//            )
//        }
        return repository.sendOrder(
            token = token,
            body = BasketRequest(
                addressId = try {
                    addressId.toInt()
                } catch (ex: Exception) {0},
                paymentMethod = paymentMethod,
                orders = products.map { p2->  Order(p2.count, p2.id.toInt())},
                bank = bank,
                addressName = addressName,
                addressValue = addressValue,
                addressPhone = addressPhone,
                isAuth = isAuth,
                guestId = guestId
            )
        )
    }
}