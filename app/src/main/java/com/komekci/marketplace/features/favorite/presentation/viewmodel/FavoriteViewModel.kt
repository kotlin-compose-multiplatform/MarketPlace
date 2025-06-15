package com.komekci.marketplace.features.favorite.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.favorite.data.entity.product.FavRequest
import com.komekci.marketplace.features.favorite.data.entity.store.FavStoreRequest
import com.komekci.marketplace.features.favorite.domain.usecase.FavoriteUseCase
import com.komekci.marketplace.features.favorite.presentation.state.FavoriteProductsState
import com.komekci.marketplace.features.favorite.presentation.state.FavoriteStoresState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val useCase: FavoriteUseCase,
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _favProducts = MutableStateFlow(FavoriteProductsState())
    val favProducts = _favProducts.asStateFlow()

    private val _favStores = MutableStateFlow(FavoriteStoresState())
    val favStores = _favStores.asStateFlow()

    fun getFavoriteStores() {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.getFavoriteStores(it.token ?: "").onEach { result ->
                        when (result) {
                            is Resource.Error -> {
                                _favStores.value = _favStores.value.copy(
                                    loading = false,
                                    error = result.message,
                                    message = result.errorMessage,
                                    code = result.code,
                                    data = result.data
                                )
                            }

                            is Resource.Loading -> {
                                _favStores.value = _favStores.value.copy(
                                    loading = true,
                                    error = result.message,
                                    message = result.errorMessage,
                                    code = result.code,
                                    data = result.data
                                )
                            }

                            is Resource.Success -> {
                                _favStores.value = _favStores.value.copy(
                                    loading = false,
                                    error = result.message,
                                    message = result.errorMessage,
                                    code = result.code,
                                    data = result.data
                                )
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun getFavoriteProducts() {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.getFavoriteProducts(it.token ?: "").onEach { result ->
                        when (result) {
                            is Resource.Error -> {
                                _favProducts.value = _favProducts.value.copy(
                                    loading = false,
                                    error = result.message,
                                    message = result.errorMessage,
                                    code = result.code,
                                    data = result.data
                                )
                            }

                            is Resource.Loading -> {
                                _favProducts.value = _favProducts.value.copy(
                                    loading = true,
                                    error = result.message,
                                    message = result.errorMessage,
                                    code = result.code,
                                    data = result.data
                                )
                            }

                            is Resource.Success -> {
                                val distinctProducts = result.data?.distinctBy { it.id } ?: emptyList()
                                _favProducts.value = _favProducts.value.copy(
                                    loading = false,
                                    error = result.message,
                                    message = result.errorMessage,
                                    code = result.code,
                                    data = distinctProducts
                                )
                                println("LIKEDD: ${result.data?.size}")
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun getIsLiked(id: String): Boolean {
        return _favProducts.value.data?.indexOfFirst { it.id == try {
            id.toInt()
        } catch (_: Exception) {0} } == -1
    }

    fun getStoreIsLiked(id: String): Boolean {
        return _favStores.value.data?.indexOfFirst { it.id == try {
            id.toInt()
        } catch (_: Exception) {0} } == -1
    }

    fun likeProduct(id: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            userDataStore.getUserData {
                println("LIKE-3: ${id}")
                viewModelScope.launch {
                    useCase.likeProduct(
                        it.token ?: "", FavRequest(
                            productId = try {
                                id.toInt()
                            } catch (_: Exception) {0},
                            isLiked = getIsLiked(id)
                        )
                    ).onEach { result ->
                        when (result) {
                            is Resource.Error -> {
                                println("LIKE-4: ${result.message}")
                            }
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                println("LIKE-5: ${id}")
                                onSuccess()
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun likeStore(id: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.likeStore(
                        it.token ?: "", FavStoreRequest(
                            storeId = try {
                                id.toInt()
                            } catch (_: Exception) {0},
                            isLiked = getStoreIsLiked(id)
                        )
                    ).onEach { result ->
                        when (result) {
                            is Resource.Error -> {}
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                onSuccess()
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun initFavorites() {
        if (_favProducts.value.data.isNullOrEmpty()) {
            getFavoriteProducts()
        }
        if(_favStores.value.data.isNullOrEmpty()) {
            getFavoriteStores()
        }
    }


}