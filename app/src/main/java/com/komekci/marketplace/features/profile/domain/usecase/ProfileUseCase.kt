package com.komekci.marketplace.features.profile.domain.usecase

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
import com.komekci.marketplace.features.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class ProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend fun addAddress(title: String, address: String, token: String): Flow<Resource<AddressResponse?>> {
        return profileRepository.addAddress(
            body = CreateAddressRequest(address, title),
            token = token
        )
    }

    suspend fun updateAddress(title: String, address: String, id: String, token: String): Flow<Resource<AddressResponse?>> {
        return profileRepository.updateAddress(
            body = CreateAddressRequest(address, title),
            id = id,
            token = token
        )
    }
    suspend fun deleteAddress(id: String, token: String): Flow<Resource<Unit>> {
        return profileRepository.deleteAddress(id, token)
    }

    suspend fun getAddress(token: String): Flow<Resource<List<AddressResponse>?>> {
        return profileRepository.getAddress(token)
    }

    suspend fun getProfile(token: String): Flow<Resource<SecondProfileEntity?>> {
        return profileRepository.getProfile(token)
    }
    suspend fun updateUser(token: String, body: UpdateUser): Flow<Resource<UpdateUserResponse?>> {
        return profileRepository.updateUser(token, body)
    }
    suspend fun getMyOrders(token: String, guestId: String, isLoggedIn: Boolean): Flow<Resource<List<OrderResponseItem>>> {
        return profileRepository.getMyOrders(token,guestId, isLoggedIn)
    }
    suspend fun getPaymentHistory(token: String, body: GetPaymentBody): Flow<Resource<List<Payment>>> {
        return profileRepository.getPaymentHistory(token, body)
    }
    suspend fun payWithKey(token: String, key: String): Flow<Resource<PaymentHistory>> {
        return profileRepository.payWithKey(token, PayBody(key))
    }
    suspend fun deleteUserImage(token: String): Flow<Resource<String>> {
        return profileRepository.deleteUserImage(token)
    }
    suspend fun getSingleOrder(token: String, id: String, guestId: String): Flow<Resource<SingleOrder>> {
        return profileRepository.getSingleOrder(token, id,guestId)
    }
    suspend fun getStoreOrders(token: String, request: StoreOrderRequest): Flow<Resource<List<OrderResponseItem>>> {
        return profileRepository.getStoreOrders(token, request)
    }
    suspend fun getStoreSingleOrder(token: String, id: String): Flow<Resource<SingleOrder>> {
        return profileRepository.getStoreSingleOrder(token, id)
    }
    suspend fun updateStoreOrder(token: String, orderId: String, request: StoreOrderStatus): Flow<Resource<Boolean>> {
        return profileRepository.updateStoreOrder(token, orderId, request)
    }
    suspend fun getPrivacyPolicy(): Flow<Resource<PrivacyPolicyEntity>> {
        return profileRepository.getPrivacyPolicy()
    }
}