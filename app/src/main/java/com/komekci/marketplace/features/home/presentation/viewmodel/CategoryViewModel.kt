package com.komekci.marketplace.features.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.home.data.entity.country.CountryRequest
import com.komekci.marketplace.features.home.domain.model.SearchRequest
import com.komekci.marketplace.features.home.domain.usecase.CategoryUseCase
import com.komekci.marketplace.features.home.presentation.state.CategoryState
import com.komekci.marketplace.features.home.presentation.state.HomeProductState
import com.komekci.marketplace.features.home.presentation.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val useCase: CategoryUseCase,
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(CategoryState())
    val state: StateFlow<CategoryState> = _state.asStateFlow()

    private val _searchState = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()

    private val _homeProductState = MutableStateFlow(HomeProductState())
    val homeProductState = _homeProductState.asStateFlow()

    fun getHomeProducts(request: CountryRequest) {
        viewModelScope.launch {
            useCase.getHomeProducts(request).onEach {
                when (it) {
                    is Resource.Error -> {
                        _homeProductState.value = _homeProductState.value.copy(
                            data = it.data,
                            loading = false,
                            error = true
                        )
                    }
                    is Resource.Loading -> {
                        _homeProductState.value = _homeProductState.value.copy(
                            data = it.data,
                            loading = true,
                            error = false
                        )
                    }
                    is Resource.Success -> {
                        _homeProductState.value = _homeProductState.value.copy(
                            data = it.data,
                            loading = false,
                            error = false
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            useCase().onEach {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            categories = it.data,
                            loading = true,
                            error = false,
                            isEmpty = true
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            categories = it.data,
                            loading = false,
                            error = true,
                            isEmpty = true
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            categories = it.data,
                            loading = false,
                            error = false,
                            isEmpty = it.data.isNullOrEmpty()
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun searchProducts(text: String,language: String) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.searchProducts(
                        request = SearchRequest(
                            language = language,
                            token = it.token?:"",
                            product = text,
                            store = "",
                            category = ""
                        )
                    ).onEach { result->
                        when(result) {
                            is Resource.Error -> {
                                _searchState.value = _searchState.value.copy(
                                    loading = false,
                                    products = result.data,
                                    error = result.message
                                )
                            }
                            is Resource.Loading -> {
                                _searchState.value = _searchState.value.copy(
                                    loading = true,
                                    products = result.data,
                                    error = result.message
                                )
                            }
                            is Resource.Success -> {
                                _searchState.value = _searchState.value.copy(
                                    loading = false,
                                    products = result.data,
                                    error = result.message
                                )
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun initCategories() {
        if(_state.value.categories.isNullOrEmpty()) {
            getCategories()
        }
    }
}