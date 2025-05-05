package com.komekci.marketplace.features.profile.data.entity.order

import androidx.annotation.Keep

@Keep
enum class OrderStatus(status: String) {
    COMPLETED("completed"),
    CANCELED("cancelled"),
    ON_THE_WAY("on the way"),
    WAITING("waiting")
}

@Keep
data class OrderResponseItem(
    val address: Address,
    val bank: Any,
    val createdAt: String,
    val description: String?,
    val id: Int,
    val isPaidBank: Boolean?,
    val isView: Boolean,
    val key: String,
    val orderItems: List<OrderItem>,
    val paymentMethod: String,
    val status: String,
    val storeName: String,
    val totalPrice: Double?,
    val updatedAt: String,
    val user: OrderItemUser? = null
)

@Keep
data class OrderItemUser(
    val name: String? = null,
    val phoneNumber: String? = null,
)

