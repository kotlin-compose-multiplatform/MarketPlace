package com.komekci.marketplace.features.create_store.domain.usecase

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.create_store.data.entity.add_product.AddProductRequest
import com.komekci.marketplace.features.create_store.data.entity.add_product.SingleProduct
import com.komekci.marketplace.features.create_store.data.entity.add_product.UploadProductImage
import com.komekci.marketplace.features.create_store.data.entity.edit.EditStoreResponse
import com.komekci.marketplace.features.create_store.data.entity.edit.UpdateStore
import com.komekci.marketplace.features.create_store.data.entity.location.AddLocationBody
import com.komekci.marketplace.features.create_store.data.entity.online.OnlinePayment
import com.komekci.marketplace.features.create_store.data.entity.online.StoreTokenEntity
import com.komekci.marketplace.features.create_store.data.entity.params.ParamRequest
import com.komekci.marketplace.features.create_store.data.entity.payment.StorePayment
import com.komekci.marketplace.features.create_store.data.entity.product.MyProductsItem
import com.komekci.marketplace.features.create_store.data.entity.store.CreateStoreBody
import com.komekci.marketplace.features.create_store.data.entity.store.CreateStoreResponse
import com.komekci.marketplace.features.create_store.data.entity.store.MyStore
import com.komekci.marketplace.features.create_store.domain.model.ProductParams
import com.komekci.marketplace.features.create_store.domain.repository.StoreRepository
import com.komekci.marketplace.features.profile.data.entity.payments.GetPaymentBody
import kotlinx.coroutines.flow.Flow
import java.io.File

class StoreUseCase(
    private val repository: StoreRepository
) {
    suspend fun getMyStore(storeToken: String): Flow<Resource<MyStore>> {
        return repository.getMyStore(storeToken)
    }
    suspend fun updateStore(storeToken: String, body: UpdateStore): Flow<Resource<EditStoreResponse>> {
        return repository.updateStore(storeToken, body)
    }
    suspend fun getMyProducts(storeToken: String): Flow<Resource<List<MyProductsItem>>> {
        return repository.getMyProducts(storeToken)
    }
    suspend fun getMyProductById(storeToken: String, productId: Int): Flow<Resource<SingleProduct>> {
        return repository.getMyProductById(storeToken, productId)
    }
    suspend fun getParams(storeToken: String, body: ParamRequest): Flow<Resource<ProductParams>> {
        return repository.getParams(storeToken, body)
    }
    suspend fun uploadProductImage(storeToken: String, files: List<File>,  onDone: (List<UploadProductImage>)-> Unit): Flow<Resource<UploadProductImage>> {
        return repository.uploadProductImage(storeToken, files, onDone)
    }
    suspend fun addProduct(storeToken: String, body: AddProductRequest): Flow<Resource<AddProductRequest>> {
        return repository.addProduct(storeToken, body)
    }
    suspend fun deleteProducts(storeToken: String, id: String): Flow<Resource<Unit>> {
        return repository.deleteProducts(storeToken, id)
    }
    suspend fun editProduct(storeToken: String, id: String, body: AddProductRequest): Flow<Resource<AddProductRequest>> {
        return repository.editProduct(storeToken, id, body)
    }
    suspend fun addStoreLocation(storeToken: String, body: AddLocationBody): Flow<Resource<Unit>> {
        return repository.addStoreLocation(storeToken, body)
    }
    suspend fun deleteLocation(storeToken: String, id: String): Flow<Resource<Unit>> {
        return repository.deleteLocation(storeToken, id)
    }
    suspend fun updateStoreLocation(storeToken: String, id: String, body: AddLocationBody): Flow<Resource<Unit>> {
        return repository.updateStoreLocation(storeToken, id, body)
    }
    suspend fun getStorePayments(storeToken: String, body: GetPaymentBody): Flow<Resource<List<StorePayment>>> {
        return repository.getStorePayments(storeToken, body)
    }
    suspend fun createStore(type: String, token: String, body: CreateStoreBody): Flow<Resource<CreateStoreResponse>> {
        return repository.createStore(type, token, body)
    }

    suspend fun createStoreOnline(type: String,token: String, body: CreateStoreBody): Flow<Resource<OnlinePayment>> {
        return repository.createStoreOnline(type, token, body)
    }

    suspend fun getStoreToken(f_id: String): Flow<Resource<StoreTokenEntity>> {
        return repository.getStoreToken(f_id)
    }
}