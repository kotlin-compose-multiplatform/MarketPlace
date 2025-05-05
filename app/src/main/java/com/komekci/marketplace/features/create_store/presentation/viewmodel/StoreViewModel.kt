package com.komekci.marketplace.features.create_store.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.create_store.data.entity.add_product.AddProductRequest
import com.komekci.marketplace.features.create_store.data.entity.add_product.En
import com.komekci.marketplace.features.create_store.data.entity.add_product.ProductPrice
import com.komekci.marketplace.features.create_store.data.entity.add_product.Ru
import com.komekci.marketplace.features.create_store.data.entity.add_product.SingleProduct
import com.komekci.marketplace.features.create_store.data.entity.add_product.Tm
import com.komekci.marketplace.features.create_store.data.entity.add_product.Translations
import com.komekci.marketplace.features.create_store.data.entity.edit.UpdateStore
import com.komekci.marketplace.features.create_store.data.entity.location.AddLocationBody
import com.komekci.marketplace.features.create_store.data.entity.params.ParamRequest
import com.komekci.marketplace.features.create_store.data.entity.product.MyProductsItem
import com.komekci.marketplace.features.create_store.data.entity.store.CreateStoreBody
import com.komekci.marketplace.features.create_store.domain.usecase.StoreUseCase
import com.komekci.marketplace.features.create_store.presentation.state.AddLocationState
import com.komekci.marketplace.features.create_store.presentation.state.AddProductState
import com.komekci.marketplace.features.create_store.presentation.state.CreateStoreState
import com.komekci.marketplace.features.create_store.presentation.state.CreateStoreStateOnline
import com.komekci.marketplace.features.create_store.presentation.state.DeleteLocationState
import com.komekci.marketplace.features.create_store.presentation.state.DeleteState
import com.komekci.marketplace.features.create_store.presentation.state.EditProductState
import com.komekci.marketplace.features.create_store.presentation.state.MyProductsState
import com.komekci.marketplace.features.create_store.presentation.state.MyStoreState
import com.komekci.marketplace.features.create_store.presentation.state.ParamState
import com.komekci.marketplace.features.create_store.presentation.state.StorePaymentState
import com.komekci.marketplace.features.create_store.presentation.state.UpdateStoreState
import com.komekci.marketplace.features.create_store.presentation.state.UploadPImageState
import com.komekci.marketplace.features.profile.data.entity.payments.GetPaymentBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.io.File
import java.time.Duration
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val useCase: StoreUseCase,
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _myStore = MutableStateFlow(MyStoreState())
    val myStore = _myStore.asStateFlow()

    private val _myProducts = MutableStateFlow(MyProductsState())
    val myProducts = _myProducts.asStateFlow()

    private val _params = MutableStateFlow(ParamState())
    val params = _params.asStateFlow()

    val createStoreState = mutableStateOf(CreateStoreState())
    val createStoreStateOnline = mutableStateOf(CreateStoreStateOnline())

    var updateStoreState = mutableStateOf(UpdateStoreState())
        private set

    var uploadProductImageState = mutableStateOf(UploadPImageState())
        private set

    var addProductState = mutableStateOf(AddProductState())
        private set

    var editProductState = mutableStateOf(EditProductState())
        private set

    var addLocationState = mutableStateOf(AddLocationState())
        private set

    var selectedProduct = mutableStateOf<SingleProduct?>(null)
        private set

    var singleProduct = mutableStateOf<SingleProduct?>(null)
        private set

    var deleteState = mutableStateOf(DeleteState())
        private set

    var deleteLocationState = mutableStateOf(DeleteLocationState())
        private set

    private val _payments = MutableStateFlow(StorePaymentState())
    val payments = _payments.asStateFlow()

    fun selectProduct(product: MyProductsItem?) {
//        selectedProduct.value = product
        if(product!=null) {
            getSingleProduct(product.id)
        } else {
            singleProduct.value = null
        }
    }

    fun getSingleProduct(productId: Int) {
        viewModelScope.launch {
            userDataStore.getUserData { user->
                viewModelScope.launch {
                    useCase.getMyProductById(user.store_token?:"", productId).onEach { result->
                        when(result) {
                            is Resource.Error -> {}
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                singleProduct.value = result.data
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun getStoreToken(f_id: String, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.getStoreToken(f_id).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                userDataStore.saveUserData(it.copy(store_token = result.data?.storeToken))
                                getMyStore(
                                    onSuccess = onSuccess
                                )
                            }

                            else -> {}
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun createStore(type: String,body: CreateStoreBody, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.createStore(
                        token = if(type=="NEW") it.token?:"" else it.store_token?:"",
                        body = body,
                        type = type
                    ).onEach {result->
                        when(result) {
                            is Resource.Error -> {
                                createStoreState.value = createStoreState.value.copy(
                                    loading = false,
                                    error = result.message,
                                    message = result.errorMessage,
                                )
                            }
                            is Resource.Loading -> {
                                createStoreState.value = createStoreState.value.copy(
                                    loading = true,
                                    error = result.message,
                                    message = result.errorMessage,
                                )
                            }
                            is Resource.Success -> {
                                createStoreState.value = createStoreState.value.copy(
                                    loading = false,
                                    error = result.message,
                                    message = result.errorMessage,
                                )
                                if(body.paymentMethod == "key") {
                                    userDataStore.saveUserData(it.copy(store_token = result.data?.storeToken))
                                    getMyStore()
                                }
                                onSuccess()
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun createStoreOnline(type: String,body: CreateStoreBody, onSuccess: () -> Unit) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.createStoreOnline(
                        token = if(type=="NEW") it.token?:"" else it.store_token?:"",
                        body = body,
                        type = type
                    ).onEach {result->
                        when(result) {
                            is Resource.Error -> {
                                createStoreStateOnline.value = createStoreStateOnline.value.copy(
                                    loading = false,
                                    error = result.message,
                                    message = result.errorMessage,
                                    data = result.data
                                )
                            }
                            is Resource.Loading -> {
                                createStoreStateOnline.value = createStoreStateOnline.value.copy(
                                    loading = true,
                                    error = result.message,
                                    message = result.errorMessage,
                                    data = result.data
                                )
                            }
                            is Resource.Success -> {
                                createStoreStateOnline.value = createStoreStateOnline.value.copy(
                                    loading = false,
                                    error = result.message,
                                    message = result.errorMessage,
                                    data = result.data
                                )
                                if(result.data!=null) {
                                    onSuccess()
                                }
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
                    useCase.getStorePayments(
                        it.store_token ?: "",
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

    fun addLocation(region: String, city: String, onSuccess: ()-> Unit) {
        viewModelScope.launch {
            userDataStore.getUserData { user ->
                viewModelScope.launch {
                    useCase.addStoreLocation(
                        user.store_token ?: "",
                        AddLocationBody(region.toInt(), city.toInt())
                    ).onEach {
                        when(it) {
                            is Resource.Error -> {
                                addLocationState.value = addLocationState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage
                                )
                            }
                            is Resource.Loading -> {
                                addLocationState.value = addLocationState.value.copy(
                                    loading = true,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage
                                )
                            }
                            is Resource.Success -> {
                                addLocationState.value = addLocationState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage
                                )
                                onSuccess()
                                getMyStore()
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun updateLocation(region: String, id: String, city: String, onSuccess: ()-> Unit) {
        viewModelScope.launch {
            userDataStore.getUserData { user ->
                viewModelScope.launch {
                    useCase.updateStoreLocation(
                        user.store_token ?: "",
                        id,
                        AddLocationBody(region.toInt(), city.toInt())
                    ).onEach {
                        when(it) {
                            is Resource.Error -> {
                                addLocationState.value = addLocationState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage
                                )
                            }
                            is Resource.Loading -> {
                                addLocationState.value = addLocationState.value.copy(
                                    loading = true,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage
                                )
                            }
                            is Resource.Success -> {
                                addLocationState.value = addLocationState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage
                                )
                                onSuccess()
                                getMyStore()
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun addProduct(
        name_tm: String,
        name_en: String,
        name_ru: String,
        desc_tm: String,
        desc_ru: String,
        desc_en: String,
        catId: Int,
        catalogId: Int,
        subCatId: Int,
        brandId: Int,
        imagesId: List<Int>,
        price: List<ProductPrice>,
        discount: String,
        code: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            userDataStore.getUserData { user ->
                viewModelScope.launch {
                    val body = AddProductRequest(
                        brandId = brandId.toString(),
                        catalogId = catalogId.toString(),
                        categoryId = catId.toString(),
                        code = code,
                        discount = discount,
                        imagesId = imagesId,
                        prices = price,
                        subCatalogId = subCatId.toString(),
                        translations = Translations(
                            en = En(
                                description = desc_en,
                                name = name_en
                            ),
                            ru = Ru(
                                description = desc_ru,
                                name = name_ru
                            ),
                            tm = Tm(
                                description = desc_tm,
                                name = name_tm
                            )
                        )
                    )
                    useCase.addProduct(user.store_token ?: "", body).onEach {
                        when (it) {
                            is Resource.Error -> {
                                addProductState.value = addProductState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage,
                                    data = it.data
                                )
                            }

                            is Resource.Loading -> {
                                addProductState.value = addProductState.value.copy(
                                    loading = true,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage,
                                    data = it.data
                                )
                            }

                            is Resource.Success -> {
                                addProductState.value = addProductState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage,
                                    data = it.data
                                )
                                getMyProducts()
                                onSuccess()
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun editProduct(
        id: String,
        name_tm: String,
        name_en: String,
        name_ru: String,
        desc_tm: String,
        desc_ru: String,
        desc_en: String,
        catId: Int,
        catalogId: Int,
        subCatId: Int,
        brandId: Int,
        imagesId: List<Int>,
        price: List<ProductPrice>,
        discount: String,
        code: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            userDataStore.getUserData { user ->
                viewModelScope.launch {
                    val body = AddProductRequest(
                        brandId = brandId.toString(),
                        catalogId = catalogId.toString(),
                        categoryId = catId.toString(),
                        code = code,
                        discount = discount,
                        imagesId = imagesId,
                        prices = price,
                        subCatalogId = subCatId.toString(),
                        translations = Translations(
                            en = En(
                                description = desc_en,
                                name = name_en
                            ),
                            ru = Ru(
                                description = desc_ru,
                                name = name_ru
                            ),
                            tm = Tm(
                                description = desc_tm,
                                name = name_tm
                            )
                        )
                    )
                    useCase.editProduct(user.store_token ?: "", id, body).onEach {
                        when (it) {
                            is Resource.Error -> {
                                editProductState.value = editProductState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage,
                                    data = it.data
                                )
                            }

                            is Resource.Loading -> {
                                editProductState.value = editProductState.value.copy(
                                    loading = true,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage,
                                    data = it.data
                                )
                            }

                            is Resource.Success -> {
                                editProductState.value = editProductState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage,
                                    data = it.data
                                )
                                getMyProducts()
                                onSuccess()
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun getMyStore(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            userDataStore.getUserData { user ->
                viewModelScope.launch {
                    useCase.getMyStore(user.store_token ?: "").onEach {
                        when (it) {
                            is Resource.Error -> {
                                _myStore.value = _myStore.value.copy(
                                    loading = false,
                                    error = it.message,
                                    messages = it.errorMessage,
                                    code = it.code,
                                    data = it.data
                                )
                            }

                            is Resource.Loading -> {
                                _myStore.value = _myStore.value.copy(
                                    loading = true,
                                    error = it.message,
                                    messages = it.errorMessage,
                                    code = it.code,
                                    data = it.data
                                )
                            }

                            is Resource.Success -> {
                                _myStore.value = _myStore.value.copy(
                                    loading = false,
                                    error = it.message,
                                    messages = it.errorMessage,
                                    code = it.code,
                                    data = it.data
                                )
                                onSuccess()
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun uploadImages(
        images: List<File>,
        onDone: (List<Int>) -> Unit
    ) {
        viewModelScope.launch {
            userDataStore.getUserData { user ->
                viewModelScope.launch {
                    uploadProductImageState.value = uploadProductImageState.value.copy(
                        loading = true,
                        error = null,
                        result = emptyList()
                    )
                    useCase.uploadProductImage(user.store_token ?: "", images, onDone = {
                        Log.e("STATUS", it.toString())
                        onDone(it.filter { v -> v.id != -1 }.map { v -> v.id })
                        uploadProductImageState.value = uploadProductImageState.value.copy(
                            loading = false,
                            error = null,
                            result = it
                        )
                    }).launchIn(this)
                }
            }
        }
    }

    fun updateStore(
        name: String,
        phone: String,
        instagram: String,
        tiktok: String,
        image: File?,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.updateStore(
                        storeToken = it.store_token ?: "",
                        body = UpdateStore(
                            name = name,
                            phone = phone,
                            image = image,
                            instagram = instagram,
                            tiktok = tiktok
                        )
                    ).onEach { res ->
                        when (res) {
                            is Resource.Error -> {
                                updateStoreState.value = updateStoreState.value.copy(
                                    loading = false,
                                    error = res.message,
                                    message = res.errorMessage,
                                    data = res.data,
                                    code = res.code
                                )
                            }

                            is Resource.Loading -> {
                                updateStoreState.value = updateStoreState.value.copy(
                                    loading = true,
                                    error = res.message,
                                    message = res.errorMessage,
                                    data = res.data,
                                    code = res.code
                                )
                            }

                            is Resource.Success -> {
                                updateStoreState.value = updateStoreState.value.copy(
                                    loading = false,
                                    error = res.message,
                                    message = res.errorMessage,
                                    data = res.data,
                                    code = res.code
                                )
                                kotlinx.coroutines.delay(500L)
                                getMyStore()
                                onSuccess()
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    var myProductJob: Job? = null

    fun getMyProducts() {
        myProductJob?.cancel()
        myProductJob = viewModelScope.launch {
            userDataStore.getUserData { user ->
                viewModelScope.launch {
                    useCase.getMyProducts(user.store_token ?: "").onEach {
                        when (it) {
                            is Resource.Error -> {
                                _myProducts.value = _myProducts.value.copy(
                                    loading = false,
                                    error = it.message,
                                    message = it.errorMessage,
                                    code = it.code,
                                    data = it.data
                                )
                            }

                            is Resource.Loading -> {
                                _myProducts.value = _myProducts.value.copy(
                                    loading = true,
                                    error = it.message,
                                    message = it.errorMessage,
                                    code = it.code,
                                    data = it.data
                                )
                            }

                            is Resource.Success -> {
                                _myProducts.value = _myProducts.value.copy(
                                    loading = false,
                                    error = it.message,
                                    message = it.errorMessage,
                                    code = it.code,
                                    data = it.data
                                )
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun deleteProduct(id: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            userDataStore.getUserData { user ->
                viewModelScope.launch {
                    useCase.deleteProducts(user.store_token ?: "", id).onEach {
                        when (it) {
                            is Resource.Error -> {
                                deleteState.value = deleteState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage
                                )
                            }

                            is Resource.Loading -> {
                                deleteState.value = deleteState.value.copy(
                                    loading = true,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage
                                )
                            }

                            is Resource.Success -> {
                                deleteState.value = deleteState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage
                                )
                                onSuccess()
                                getMyProducts()
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun deleteLocation(id: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            userDataStore.getUserData { user ->
                viewModelScope.launch {
                    useCase.deleteLocation(user.store_token ?: "", id).onEach {
                        when (it) {
                            is Resource.Error -> {
                                deleteLocationState.value = deleteLocationState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage
                                )
                            }

                            is Resource.Loading -> {
                                deleteLocationState.value = deleteLocationState.value.copy(
                                    loading = true,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage
                                )
                            }

                            is Resource.Success -> {
                                deleteLocationState.value = deleteLocationState.value.copy(
                                    loading = false,
                                    error = it.message,
                                    code = it.code,
                                    message = it.errorMessage
                                )
                                onSuccess()
                                getMyStore()
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }


    fun getParams() {
        viewModelScope.launch {
            userDataStore.getUserData { user ->
                viewModelScope.launch {
                    useCase.getParams(user.store_token ?: "", ParamRequest()).onEach {
                        when (it) {
                            is Resource.Error -> {
                                _params.value = _params.value.copy(
                                    loading = false,
                                    error = it.message,
                                    params = it.data
                                )
                            }

                            is Resource.Loading -> {
                                _params.value = _params.value.copy(
                                    loading = true,
                                    error = it.message,
                                    params = it.data
                                )
                            }

                            is Resource.Success -> {
                                _params.value = _params.value.copy(
                                    loading = false,
                                    error = it.message,
                                    params = it.data
                                )
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun initMyProducts() {
        if (_myProducts.value.data.isNullOrEmpty()) {
            getMyProducts()
        }
    }

    fun initParams() {
        if (_params.value.params == null) {
            getParams()
        }
    }

    fun initMyStore() {
        if (_myStore.value.data == null) {
            getMyStore()
        }
    }
}