package com.komekci.marketplace.features.profile.data.entity.order

import androidx.annotation.Keep

@Keep
data class SingleOrder(
    val address: AddressX?,
    val bank: Any?,
    val createdAt: String?,
    val description: String?,
    val discount: Discount,
    val id: Int,
    val isPaidBank: Boolean?,
    val isView: Boolean,
    val key: String,
    val orderItems: List<OrderItemX>,
    val paymentMethod: String,
    val status: String,
    val storeName: String,
    val storePhonenumber: String?,
    val totalPrice: Double?,
    val updatedAt: String?
)