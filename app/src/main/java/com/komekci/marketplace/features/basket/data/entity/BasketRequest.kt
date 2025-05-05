package com.komekci.marketplace.features.basket.data.entity

import androidx.annotation.Keep

@Keep
data class BasketRequest(
    val addressId: Int,
    val orders: List<Order>,
    val paymentMethod: String,
    val addressName: String,
    val addressValue: String,
    val addressPhone: String,
    val isAuth: Boolean,
    val bank: Int = 0,
    val guestId: String,
) {
    fun toGuest(): BasketRequestGuest {
        return BasketRequestGuest(
            orders = orders,
            paymentMethod = paymentMethod,
            bank = bank,
            guest = Guest(
                name = addressName,
                address = addressValue,
                phoneNumber = addressPhone
            )
        )
    }
}

@Keep
data class BasketRequestGuest(
    val orders: List<Order>,
    val paymentMethod: String,
    val bank: Int = 0,
    val guest: Guest
)

@Keep
data class Guest(
    val name: String? = null,
    val address: String? = null,
    val phoneNumber: String? = null,
)