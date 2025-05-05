package com.komekci.marketplace.features.auth.presentation.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.auth.data.entity.LocalUserEntity
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.auth.domain.usecase.AuthUseCase
import com.komekci.marketplace.features.auth.presentation.state.AuthType
import com.komekci.marketplace.features.auth.presentation.state.CheckCodeState
import com.komekci.marketplace.features.auth.presentation.state.CountryState
import com.komekci.marketplace.features.auth.presentation.state.LoginState
import com.komekci.marketplace.features.auth.presentation.state.RegisterState
import com.komekci.marketplace.features.auth.presentation.state.SendCodeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _username = MutableStateFlow("")
    private val _phoneNumber = MutableStateFlow("")
    private val _code = MutableStateFlow("")
    val countryState = mutableStateOf(CountryState())
    private val _selectedCountryId = MutableStateFlow<Int?>(null)
    private val _selectedRegion = MutableStateFlow<Int?>(null)
    val selectedCountryId: StateFlow<Int?> = _selectedCountryId.asStateFlow()
    val selectedRegion: StateFlow<Int?> = _selectedRegion.asStateFlow()
    val username: StateFlow<String> = _username.asStateFlow()
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()
    val code: StateFlow<String> = _code.asStateFlow()
    var registerState = mutableStateOf(RegisterState())
        private set

    var loginState = mutableStateOf(LoginState())
        private set

    var sendCodeState = mutableStateOf(SendCodeState())
        private set

    var checkCodeState = mutableStateOf(CheckCodeState())
        private set

    var index = mutableIntStateOf(0)
        private set

    fun updateUsername(value: String) {
        _username.value = value
    }
    fun updatePhoneNumber(value: String) {
        _phoneNumber.value = value
    }

    fun updateSelectedCountryId(value: Int?) {
        _selectedCountryId.value = value
    }

    fun updateSelectedRegion(value: Int?) {
        _selectedRegion.value = value
    }

    fun updateCode(value: String) {
        _code.value = value
    }
    
    fun register(onSuccess: () -> Unit) {
        val name = _username.value
        val phoneNumber = _phoneNumber.value
        val email = ""
        viewModelScope.launch {
            authUseCase.register(
                name = name,
                phoneNumber = phoneNumber,
                email = email,
                regionId = _selectedRegion.value,
                countryId = _selectedCountryId.value
            ).onEach { 
                when(it) {
                    is Resource.Loading -> {
                         registerState.value = registerState.value.copy(
                             email = email,
                             name = name,
                             phoneNumber = phoneNumber,
                             message = it.errorMessage,
                             response = it.data,
                             error = it.message,
                             loading = true
                         )
                    }
                    is Resource.Error -> {
                        registerState.value = registerState.value.copy(
                            email = email,
                            name = name,
                            phoneNumber = phoneNumber,
                            message = it.errorMessage,
                            response = it.data,
                            error = it.message,
                            loading = false
                        )
                    }
                    is Resource.Success -> {
                        registerState.value = registerState.value.copy(
                            email = email,
                            name = name,
                            phoneNumber = phoneNumber,
                            message = it.errorMessage,
                            response = it.data,
                            error = it.message,
                            loading = false
                        )
                        onSuccess()
                    }
                }
            }.launchIn(this)
        }
    }

    fun login(onSuccess: () -> Unit) {
        val phoneNumber = _phoneNumber.value
        viewModelScope.launch {
            authUseCase.login(
                phoneNumber = phoneNumber,
            ).onEach {
                when(it) {
                    is Resource.Loading -> {
                        loginState.value = loginState.value.copy(
                            loading = true,
                            message = it.errorMessage,
                            error = it.message,
                            data = it.data
                        )
                    }
                    is Resource.Error -> {
                        loginState.value = loginState.value.copy(
                            loading = false,
                            message = it.errorMessage,
                            error = it.message,
                            data = it.data
                        )
                    }
                    is Resource.Success -> {
                        loginState.value = loginState.value.copy(
                            loading = false,
                            message = it.errorMessage,
                            error = it.message,
                            data = it.data
                        )
                        onSuccess()
                    }
                }
            }.launchIn(this)
        }
    }

    fun checkCode(userId: Int?, type: AuthType ,onSuccess: () -> Unit) {
        viewModelScope.launch {
            userId?.let { id->
                authUseCase.checkCode(id, _code.value).onEach {
                    when(it) {
                        is Resource.Loading -> {
                            checkCodeState.value = checkCodeState.value.copy(
                                loading = true,
                                data = it.data,
                                message = it.errorMessage,
                                error = it.message
                            )
                        }
                        is Resource.Error -> {
                            checkCodeState.value = checkCodeState.value.copy(
                                loading = false,
                                data = it.data,
                                message = it.errorMessage,
                                error = it.message
                            )
                        }
                        is Resource.Success -> {
                            saveUserData(
                                username = it.data?.user?.name?:"",
                                phoneNumber = it.data?.user?.phoneNumber?:"",
                                token = it.data?.user?.token?:"",
                                store_token = it.data?.store?.token?:"",
                                image = it.data?.user?.image?:"",
                                email = "",
                                id = userId.toString()
                            )
                            onSuccess()
                            checkCodeState.value = checkCodeState.value.copy(
                                loading = false,
                                data = it.data,
                                message = it.errorMessage,
                                error = it.message
                            )
                        }

                    }
                }.launchIn(this)
            }
        }
    }

    fun sendCode(userId: Int?, onSuccess: () -> Unit) {
        viewModelScope.launch {
            userId?.let { id->
                authUseCase.sendCode(id).onEach {
                    when(it) {
                        is Resource.Loading -> {
                            sendCodeState.value = sendCodeState.value.copy(
                                loading = true,
                                error = it.message,
                                message = it.errorMessage,
                                success = false
                            )
                        }
                        is Resource.Error -> {
                            sendCodeState.value = sendCodeState.value.copy(
                                loading = false,
                                error = it.message,
                                message = it.errorMessage,
                                success = false
                            )
                        }
                        is Resource.Success -> {
                            onSuccess()
                            sendCodeState.value = sendCodeState.value.copy(
                                loading = false,
                                error = it.message,
                                message = it.errorMessage,
                                success = true
                            )
                        }
                    }
                }.launchIn(this)
            }
        }
    }

    private fun saveUserData(
        username: String,
        phoneNumber: String,
        token: String,
        store_token: String,
        image: String,
        email: String,
        id: String
    ) {
        viewModelScope.launch {
            userDataStore.saveUserData(
                LocalUserEntity(
                    username = username,
                    phone = phoneNumber,
                    token = token,
                    store_token = store_token,
                    image = image,
                    email = email,
                    id = id,
                    skipAuth = "1",
                    isFirstLaunch = "0"
                )
            )
        }
    }

    fun getAllCountries() {
        viewModelScope.launch {
            authUseCase.getAllCountries().onEach {
                when(it) {
                    is Resource.Loading -> {
                        countryState.value = countryState.value.copy(
                            isLoading = true,
                            error = it.message,
                            countries = it.data?: emptyList()
                        )
                    }
                    is Resource.Error -> {
                        countryState.value = countryState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }
                    is Resource.Success -> {
                        countryState.value = countryState.value.copy(
                            isLoading = false,
                            error = it.message,
                            countries = it.data ?: emptyList()
                        )
                    }
                }
            }.launchIn(this)

        }
    }
}