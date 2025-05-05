package com.komekci.marketplace.features.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.home.domain.usecase.ShopsUseCase
import com.komekci.marketplace.features.home.presentation.state.ShopsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopsViewModel @Inject constructor(
    private val useCase: ShopsUseCase,
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _shops = MutableStateFlow(ShopsState())
    val shops: StateFlow<ShopsState> = _shops.asStateFlow()

    private val _vipShops = MutableStateFlow(ShopsState())
    val vipShops: StateFlow<ShopsState> = _vipShops.asStateFlow()


    fun getShops() {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase().onEach {
                        when (it) {
                            is Resource.Loading -> {
                                _shops.value = _shops.value.copy(
                                    shops = it.data,
                                    loading = true,
                                    error = false,
                                    isEmpty = true
                                )
                            }

                            is Resource.Error -> {
                                _shops.value = _shops.value.copy(
                                    shops = it.data,
                                    loading = false,
                                    error = true,
                                    isEmpty = true
                                )
                            }

                            is Resource.Success -> {
                                _shops.value = _shops.value.copy(
                                    shops = it.data,
                                    loading = false,
                                    error = false,
                                    isEmpty = it.data.isNullOrEmpty()
                                )
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun getVipShops() {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.getVipShops().onEach {
                        when (it) {
                            is Resource.Loading -> {
                                _vipShops.value = _vipShops.value.copy(
                                    shops = it.data,
                                    loading = true,
                                    error = false,
                                    isEmpty = true
                                )
                            }

                            is Resource.Error -> {
                                _vipShops.value = _vipShops.value.copy(
                                    shops = it.data,
                                    loading = false,
                                    error = true,
                                    isEmpty = true
                                )
                            }

                            is Resource.Success -> {
                                _vipShops.value = _vipShops.value.copy(
                                    shops = it.data,
                                    loading = false,
                                    error = false,
                                    isEmpty = it.data.isNullOrEmpty()
                                )
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    init {
        getVipShops()
        getShops()
    }
}