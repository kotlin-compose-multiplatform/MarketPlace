package com.komekci.marketplace.features.product.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.core.database.AppDatabase
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.home.data.entity.FilterRequest
import com.komekci.marketplace.features.product.data.entity.ProductRequest
import com.komekci.marketplace.features.product.domain.model.ProductsEntity
import com.komekci.marketplace.features.product.domain.model.StoreProductRequest
import com.komekci.marketplace.features.product.domain.useCase.CategoryUseCase
import com.komekci.marketplace.features.product.presentation.state.CategoryState
import com.komekci.marketplace.features.product.presentation.state.FilterState
import com.komekci.marketplace.features.product.presentation.state.ProductState
import com.komekci.marketplace.features.product.presentation.state.SingleProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val useCase: CategoryUseCase,
    private val userDataStore: UserDataStore,
    private val db: AppDatabase
) : ViewModel() {
    var loading = false
    var hasMore = true
    private val _state = MutableStateFlow(CategoryState())
    val state: StateFlow<CategoryState> = _state.asStateFlow()

    private val _products = MutableStateFlow(ProductState())
    val products: StateFlow<ProductState> = _products.asStateFlow()

    private val _singleProduct = MutableStateFlow(SingleProductState())
    val singleProduct: StateFlow<SingleProductState> = _singleProduct.asStateFlow()

    private val _filter = MutableStateFlow(FilterState())
    val filter: StateFlow<FilterState> = _filter.asStateFlow()

    var productRequest = mutableStateOf(ProductRequest(size = 20, page = 1))
        private set

    fun setRequest(request: ProductRequest) {
        productRequest.value = request
        getProducts()
        getFilters()
    }


    fun getProducts() {


        if (_products.value.loading || _products.value.hasMore.not())
            return
        var req = productRequest.value.copy(
            size = 20,
            page = productRequest.value.page + 1
        )
        if (req.page == 1) {
            hasMore = true
            _products.value = _products.value.copy(
                products = emptyList()
            )
        }
        Log.e("GET-PRODUCTS", hasMore.toString() + " / " + loading.not())
        viewModelScope.launch {
            if (loading.not() && hasMore) {
                userDataStore.getUserData {
                    viewModelScope.launch {
                        loading = true
                        req = req.copy(
                            token = it.token
                        )

                        productRequest.value = req
                        useCase.getProducts(req).onEach {
                            when (it) {
                                is Resource.Loading -> {
                                    loading = true
                                    _products.value = _products.value.copy(
                                        loading = true,
                                        error = it.message,
                                        code = it.code,
                                    )
                                }

                                is Resource.Error -> {
                                    loading = false
                                    _products.value = _products.value.copy(
                                        loading = false,
                                        error = it.message,
                                        code = it.code
                                    )
                                }

                                is Resource.Success -> {
                                    loading = false
                                    var list = it.data ?: emptyList()
                                    _products.value.products?.let { old ->
                                        list = old.plus(list)
                                    }
                                    hasMore = list.isNotEmpty()
//                                    if(list.isEmpty()) {
//                                        productRequest.value = productRequest.value.copy(
//                                            page = productRequest.value.page.minus(1)
//                                        )
//                                    }
                                    _products.value = _products.value.copy(
                                        products = list,
                                        loading = false,
                                        error = it.message,
                                        hasMore = list.isNotEmpty()
                                    )
                                }
                            }
                        }.launchIn(this)
                    }
                }
            }
        }
    }

    fun getFilters() {
        viewModelScope.launch {
            val req = FilterRequest(
                categoryId = productRequest.value.categoryId,
                catalogId = productRequest.value.catalogId
            )
            useCase.getFilters(req).onEach {
                when (it) {
                    is Resource.Error -> {
                        _filter.value = _filter.value.copy(
                            loading = false,
                            error = it.message,
                            code = it.code,
                            message = it.errorMessage,
                            data = it.data
                        )
                    }

                    is Resource.Loading -> {
                        _filter.value = _filter.value.copy(
                            loading = true,
                            error = it.message,
                            code = it.code,
                            message = it.errorMessage,
                            data = it.data
                        )
                    }

                    is Resource.Success -> {
                        _filter.value = _filter.value.copy(
                            loading = false,
                            error = it.message,
                            code = it.code,
                            message = it.errorMessage,
                            data = it.data
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun getCategories(storeId: String, region: String? = null, district: String? = null) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase(
                        request = StoreProductRequest(
                            token = it.token ?: "",
                            storeId = storeId,
                            region = region,
                            district = district
                        )
                    ).onEach {
                        when (it) {
                            is Resource.Loading -> {
                                _state.value = _state.value.copy(
                                    categories = it.data,
                                    loading = true,
                                    error = false
                                )
                            }

                            is Resource.Error -> {
                                _state.value = _state.value.copy(
                                    categories = it.data,
                                    loading = false,
                                    error = true
                                )
                            }

                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    categories = it.data,
                                    loading = false,
                                    error = false
                                )
                            }
                        }
                    }.launchIn(this)
                }
            }

        }
    }

    fun getProductById(
        id: String
    ) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.getProductById(it.token ?: "", id).onEach {
                        when (it) {
                            is Resource.Loading -> {
                                _singleProduct.value = _singleProduct.value.copy(
                                    products = it.data,
                                    loading = true,
                                    error = it.message
                                )
                            }

                            is Resource.Error -> {
                                _singleProduct.value = _singleProduct.value.copy(
                                    products = it.data,
                                    loading = false,
                                    error = it.message
                                )
                            }

                            is Resource.Success -> {
                                _singleProduct.value = _singleProduct.value.copy(
                                    products = it.data,
                                    loading = false,
                                    error = it.message
                                )
                            }
                        }
                    }.launchIn(this)
                }
            }

        }
    }

    fun initSingleProduct(id: String) {
        if (_singleProduct.value.products == null) {
            getProductById(id)
        }
    }

    fun initCategories(storeId: String, region: String? = null, district: String? = null) {
        if (_state.value.categories.isNullOrEmpty()) {
            getCategories(storeId, region, district)
        }
    }

    fun initFilters() {
        if (_filter.value.data == null) {
            getFilters()
        }
    }

    fun getBasketCount(id: String, onFind: (Int) -> Unit) {
        viewModelScope.launch {
            val old = db.basketDao().getBasketById(id)
            if (old != null) {
                onFind(old.count)
            } else {
                onFind(0)
            }
        }
    }

    fun changeCountBasket(count: Int, item: ProductsEntity, onChange: (Int) -> Unit) {
        viewModelScope.launch {
            val old = db.basketDao().getBasketById(item.id)
            if (old != null) {
                val newCount = old.count + count
                if (newCount <= 0) {
                    db.basketDao().deleteBasketById(item.id)
                    onChange(0)
                } else {
                    db.basketDao().insertBasket(old.copy(count = newCount))
                    onChange(newCount)
                }

            } else {
                db.basketDao().insertBasket(item.toBasketEntity())
                onChange(1)
            }
        }
    }
}