package com.komekci.marketplace.features.profile.domain.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.create_store.data.entity.store.StoreOrderStatus
import com.komekci.marketplace.features.profile.data.entity.AddressResponse
import com.komekci.marketplace.features.profile.data.entity.CreateAddressRequest
import com.komekci.marketplace.features.profile.data.entity.PrivacyPolicyEntity
import com.komekci.marketplace.features.profile.data.entity.SecondProfileEntity
import com.komekci.marketplace.features.profile.data.entity.UpdateUser
import com.komekci.marketplace.features.profile.data.entity.UpdateUserResponse
import com.komekci.marketplace.features.profile.data.entity.order.OrderResponseItem
import com.komekci.marketplace.features.profile.data.entity.order.SingleOrder
import com.komekci.marketplace.features.profile.data.entity.order.StoreOrderRequest
import com.komekci.marketplace.features.profile.data.entity.payments.GetPaymentBody
import com.komekci.marketplace.features.profile.data.entity.payments.PayBody
import com.komekci.marketplace.features.profile.data.entity.payments.Payment
import com.komekci.marketplace.features.profile.data.entity.payments.PaymentHistory
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun addAddress(body: CreateAddressRequest, token: String): Flow<Resource<AddressResponse?>>
    suspend fun updateAddress(body: CreateAddressRequest, id: String, token: String): Flow<Resource<AddressResponse?>>
    suspend fun deleteAddress(id: String, token: String): Flow<Resource<Unit>>
    suspend fun getAddress(token: String): Flow<Resource<List<AddressResponse>?>>
    suspend fun getProfile(token: String): Flow<Resource<SecondProfileEntity?>>
    suspend fun updateUser(token: String, body: UpdateUser): Flow<Resource<UpdateUserResponse?>>
    suspend fun getMyOrders(token: String, guestId: String, isLoggedIn: Boolean): Flow<Resource<List<OrderResponseItem>>>
    suspend fun getStoreOrders(token: String, request: StoreOrderRequest): Flow<Resource<List<OrderResponseItem>>>
    suspend fun updateStoreOrder(token: String, orderId: String, request: StoreOrderStatus): Flow<Resource<Boolean>>
    suspend fun getPaymentHistory(token: String, body: GetPaymentBody): Flow<Resource<List<Payment>>>
    suspend fun payWithKey(token: String, body: PayBody): Flow<Resource<PaymentHistory>>
    suspend fun deleteUserImage(token: String): Flow<Resource<String>>
    suspend fun getSingleOrder(token: String, id: String, guestId: String): Flow<Resource<SingleOrder>>
    suspend fun getStoreSingleOrder(token: String, id: String): Flow<Resource<SingleOrder>>
    suspend fun getPrivacyPolicy(): Flow<Resource<PrivacyPolicyEntity>>
}