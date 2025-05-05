package com.komekci.marketplace.features.profile.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.auth.data.entity.LocalUserEntity
import com.komekci.marketplace.features.auth.data.local.AppLanguage
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.chat.data.entity.Store
import com.komekci.marketplace.features.create_store.data.entity.store.StoreOrderStatus
import com.komekci.marketplace.features.profile.data.entity.UpdateUser
import com.komekci.marketplace.features.profile.data.entity.order.OrderResponseItem
import com.komekci.marketplace.features.profile.data.entity.order.StoreOrderRequest
import com.komekci.marketplace.features.profile.data.entity.payments.GetPaymentBody
import com.komekci.marketplace.features.profile.domain.usecase.ProfileUseCase
import com.komekci.marketplace.features.profile.presentation.state.AddressState
import com.komekci.marketplace.features.profile.presentation.state.CreateAddressState
import com.komekci.marketplace.features.profile.presentation.state.DeleteUserImageState
import com.komekci.marketplace.features.profile.presentation.state.OrdersState
import com.komekci.marketplace.features.profile.presentation.state.PayWithKeyState
import com.komekci.marketplace.features.profile.presentation.state.PaymentHistoryState
import com.komekci.marketplace.features.profile.presentation.state.SingleOrderState
import com.komekci.marketplace.features.profile.presentation.state.UpdateOrderState
import com.komekci.marketplace.features.profile.presentation.state.UpdateUserState
import com.komekci.marketplace.features.profile.presentation.ui.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: ProfileUseCase,
    private val userDataStore: UserDataStore
) : ViewModel() {
    var createAddressState = mutableStateOf(CreateAddressState())
        private set


    var payWithKeyState = mutableStateOf(PayWithKeyState())
        private set

    var deleteUserImageState = mutableStateOf(DeleteUserImageState())
        private set

    var updateUserState = mutableStateOf(UpdateUserState())
        private set

    private val _address = MutableStateFlow(AddressState())
    val address = _address.asStateFlow()

    private val _orders = MutableStateFlow(OrdersState())
    val orders = _orders.asStateFlow()

    private val _payments = MutableStateFlow(PaymentHistoryState())
    val payments = _payments.asStateFlow()

    private val _profile = MutableStateFlow<LocalUserEntity?>(null)
    val profile = _profile.asStateFlow()

    private val _singleOrder = MutableStateFlow(SingleOrderState())
    val singleOrder = _singleOrder.asStateFlow()

    var updateOrderState = mutableStateOf(UpdateOrderState())
        private set

    var storeOrderRequest = mutableStateOf(StoreOrderRequest())
        private set

    fun setStoreOrderRequest(request: StoreOrderRequest) {
        storeOrderRequest.value = request
    }

    fun addAddress(title: String, address: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.addAddress(
                        title = title,
                        address = address,
                        token = it.token ?: ""
                    ).onEach {
                        when (it) {
                            is Resource.Error -> {
                                createAddressState.value = createAddressState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    message = it.errorMessage,
                                    data = it.data
                                )
                            }

                            is Resource.Loading -> {
                                createAddressState.value = createAddressState.value.copy(
                                    loading = true,
                                    error = it.message,
                                    message = it.errorMessage,
                                    data = it.data
                                )
                            }

                            is Resource.Success -> {
                                createAddressState.value = createAddressState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    message = it.errorMessage,
                                    data = it.data
                                )
                                onSuccess()
                                getAddress()
//                                it.data?.let { data ->
//                                    val item = if(_address.value.data.isNullOrEmpty()) listOf(data) else _address.value.data?.plus(data)
//                                    _address.value = _address.value.copy(
//                                        data = item
//                                    )
//                                }
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun updateAddress(id: String, title: String, address: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.updateAddress(
                        title = title,
                        address = address,
                        id = id,
                        token = it.token ?: ""
                    ).onEach {
                        when (it) {
                            is Resource.Error -> {
                                createAddressState.value = createAddressState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    message = it.errorMessage,
                                    data = it.data
                                )
                            }

                            is Resource.Loading -> {
                                createAddressState.value = createAddressState.value.copy(
                                    loading = true,
                                    error = it.message,
                                    message = it.errorMessage,
                                    data = it.data
                                )
                            }

                            is Resource.Success -> {
                                createAddressState.value = createAddressState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    message = it.errorMessage,
                                    data = it.data
                                )
                                onSuccess()
                                getAddress()
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun deleteAddress(id: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.deleteAddress(
                        id = id,
                        token = it.token ?: ""
                    ).onEach {
                        when (it) {
                            is Resource.Error -> {}
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                onSuccess()
                                getAddress()
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }


    fun deleteImage(onSuccess: () -> Unit) {
        viewModelScope.launch {
            userDataStore.getUserData { user->
                viewModelScope.launch {
                    useCase.deleteUserImage(user.token?:"").onEach {
                        when(it) {
                            is Resource.Error -> {
                                deleteUserImageState.value = deleteUserImageState.value.copy(
                                    loading = false,
                                    code = it.code,
                                    message = it.errorMessage,
                                    error = it.message,
                                    data = it.data
                                )
                            }
                            is Resource.Loading -> {
                                deleteUserImageState.value = deleteUserImageState.value.copy(
                                    loading = true,
                                    code = it.code,
                                    message = it.errorMessage,
                                    error = it.message,
                                    data = it.data
                                )
                            }
                            is Resource.Success -> {
                                deleteUserImageState.value = deleteUserImageState.value.copy(
                                    loading = false,
                                    code = it.code,
                                    message = it.errorMessage,
                                    error = it.message,
                                    data = it.data
                                )
                                _profile.value = _profile.value?.copy(
                                    image = null
                                )
                                onSuccess()
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }
    fun payWithKey(key: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.payWithKey(
                        token = it.token ?: "",
                        key = key
                    ).onEach {
                        when (it) {
                            is Resource.Error -> {
                                payWithKeyState.value = payWithKeyState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    message = it.errorMessage,
                                    data = it.data,
                                    code = it.code
                                )
                            }

                            is Resource.Loading -> {
                                payWithKeyState.value = payWithKeyState.value.copy(
                                    loading = true,
                                    error = it.message,
                                    message = it.errorMessage,
                                    data = it.data,
                                    code = it.code
                                )
                            }

                            is Resource.Success -> {
                                payWithKeyState.value = payWithKeyState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    message = it.errorMessage,
                                    data = it.data,
                                    code = it.code
                                )
                                onSuccess()
                                getPaymentHistory("all","etc")
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun getAddress() {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.getAddress(it.token ?: "").onEach {
                        when (it) {
                            is Resource.Error -> {
                                _address.value = _address.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    data = it.data,
                                    isEmpty = true
                                )
                            }

                            is Resource.Loading -> {
                                _address.value = _address.value.copy(
                                    loading = true,
                                    error = it.message,
                                    code = it.code,
                                    data = it.data,
                                    isEmpty = true
                                )
                            }

                            is Resource.Success -> {
                                _address.value = _address.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    data = it.data,
                                    isEmpty = it.data.isNullOrEmpty()
                                )
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun getOrders(type: OrderType, guestId: String, isLoggedIn: Boolean) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    val fn = if(type == OrderType.USER) useCase.getMyOrders(it.token ?: "", guestId, isLoggedIn) else useCase.getStoreOrders(it.store_token ?: "", request = storeOrderRequest.value)
                    fn.onEach {
                        when (it) {
                            is Resource.Error -> {
                                _orders.value = _orders.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    data = it.data,
                                    isEmpty = true
                                )
                            }

                            is Resource.Loading -> {
                                _orders.value = _orders.value.copy(
                                    loading = true,
                                    error = it.message,
                                    code = it.code,
                                    data = it.data,
                                    isEmpty = true
                                )
                            }

                            is Resource.Success -> {
                                _orders.value = _orders.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    data = it.data,
                                    isEmpty = it.data.isNullOrEmpty()
                                )
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun initOrders(type: OrderType, guestId: String, isLoggedIn: Boolean) {
        if(_orders.value.data.isNullOrEmpty()) {
            getOrders(type, guestId, isLoggedIn)
        }
    }

    fun updateOrderStatus(status: String, orderId: String, onSuccess: ()-> Unit) {
        viewModelScope.launch {
            userDataStore.getUserData { user->
                viewModelScope.launch {
                    useCase.updateStoreOrder(user.store_token?:"", orderId, StoreOrderStatus(status)).onEach {
                        when(it) {
                            is Resource.Error -> {
                                updateOrderState.value = updateOrderState.value.copy(
                                    loading = false
                                )
                            }
                            is Resource.Loading -> {
                                updateOrderState.value = updateOrderState.value.copy(
                                    loading = true
                                )
                            }
                            is Resource.Success -> {
                                updateOrderState.value = updateOrderState.value.copy(
                                    loading = false
                                )
                                onSuccess()
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun getPaymentHistory(date: String, type: String) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.getPaymentHistory(
                        it.token ?: "",
                        body = GetPaymentBody(
                            date = date,
                            history = type
                        )
                    ).onEach {
                        when (it) {
                            is Resource.Error -> {
                                _payments.value = _payments.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    data = it.data,
                                    isEmpty = true
                                )
                            }

                            is Resource.Loading -> {
                                _payments.value = _payments.value.copy(
                                    loading = true,
                                    error = it.message,
                                    code = it.code,
                                    data = it.data,
                                    isEmpty = true
                                )
                            }

                            is Resource.Success -> {
                                _payments.value = _payments.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    data = it.data,
                                    isEmpty = it.data.isNullOrEmpty()
                                )
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }



    fun getProfile() {
        viewModelScope.launch {
            userDataStore.getUserData {
                _profile.value = it
                viewModelScope.launch {
                    useCase.getProfile(it.token ?: "").onEach { data ->
                        when (data) {
                            is Resource.Error -> {}
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                data.data?.let { user ->


                                    _profile.value = _profile.value?.copy(
                                        image = user.image,
                                        username = user.name,
                                        phone = user.phoneNumber,
                                        email = user.email,
                                        countryId = user.country?.id,
                                        regionId = user.region?.id,
                                    )
                                }
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun updateUser(
        name: String,
        phone: String,
        countryId: Int?,
        regionId: Int?,
        image: File?,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.updateUser(
                        token = it.token ?: "",
                        body = UpdateUser(
                            name = name,
                            phone = phone,
                            countryId,
                            regionId,
                            image = image
                        )
                    ).onEach { res->
                        when(res) {
                            is Resource.Error -> {
                                updateUserState.value = updateUserState.value.copy(
                                    loading = false,
                                    error = res.message,
                                    message = res.errorMessage,
                                    data = res.data
                                )
                            }
                            is Resource.Loading -> {
                                updateUserState.value = updateUserState.value.copy(
                                    loading = true,
                                    error = res.message,
                                    message = res.errorMessage,
                                    data = res.data
                                )
                            }
                            is Resource.Success -> {
                                updateUserState.value = updateUserState.value.copy(
                                    loading = false,
                                    error = res.message,
                                    message = res.errorMessage,
                                    data = res.data
                                )
                                countryId?.let { cid->
                                    userDataStore.saveCountryId(cid.toString())
                                }

                                regionId?.let { rid->
                                    userDataStore.saveRegionId(rid.toString())
                                }
                                onSuccess()
                                res.data?.let { user->
                                    _profile.value = _profile.value?.copy(
                                        username = name,
                                        phone = phone,
                                        image = user.user.image
                                    )
                                }
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun getSingleOrder(orderId: String, type: OrderType, guestId: String) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    val fn = if(type == OrderType.USER) useCase.getSingleOrder(it.token?:"", orderId, guestId) else useCase.getStoreSingleOrder(it.store_token?:"", orderId)
                    fn.onEach { result->
                        when(result) {
                            is Resource.Error -> {
                                println("[STORE_SINGLE_ORDER] ${result.message} ERROR")
                                _singleOrder.value = _singleOrder.value.copy(
                                    loading = false,
                                    error = result.message,
                                    data = result.data
                                )
                            }
                            is Resource.Loading -> {
                                println("[STORE_SINGLE_ORDER] LOADING")
                                _singleOrder.value = _singleOrder.value.copy(
                                    loading = true,
                                    error = result.message,
                                    data = result.data
                                )
                            }
                            is Resource.Success -> {
                                _singleOrder.value = _singleOrder.value.copy(
                                    loading = false,
                                    error = result.message,
                                    data = result.data
                                )
                                println("[STORE_SINGLE_ORDER] ${result.data?.id}")
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }


    fun selectOrder(orderResponseItem: OrderResponseItem?, orderType: OrderType, guestId: String) {
        if(orderResponseItem!=null) {
            getSingleOrder(orderResponseItem.id.toString(), orderType, guestId)
        } else {
            _singleOrder.value = _singleOrder.value.copy(
                data = null
            )
        }
        _orders.value = _orders.value.copy(
            selectedOrder = orderResponseItem
        )
    }

    fun logout() {
        viewModelScope.launch {
            userDataStore.logout()
            getProfile()
        }
    }

    fun initProfile() {
        if (_profile.value == null) {
            getProfile()
        }
    }

    fun initAddress() {
        if (_address.value.data.isNullOrEmpty()) {
            getAddress()
        }
    }
}