package com.komekci.marketplace.features.create_store.domain.repository

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
import com.komekci.marketplace.features.profile.data.entity.payments.GetPaymentBody
import kotlinx.coroutines.flow.Flow
import java.io.File

interface StoreRepository {
    suspend fun getMyStore(storeToken: String): Flow<Resource<MyStore>>
    suspend fun updateStore(storeToken: String, body: UpdateStore): Flow<Resource<EditStoreResponse>>
    suspend fun getMyProducts(storeToken: String): Flow<Resource<List<MyProductsItem>>>
    suspend fun getMyProductById(storeToken: String, productId: Int): Flow<Resource<SingleProduct>>
    suspend fun getParams(storeToken: String, body: ParamRequest): Flow<Resource<ProductParams>>
    suspend fun uploadProductImage(storeToken: String, files: List<File>, onDone: (List<UploadProductImage>)-> Unit): Flow<Resource<UploadProductImage>>
    suspend fun addProduct(storeToken: String, body: AddProductRequest): Flow<Resource<AddProductRequest>>
    suspend fun editProduct(storeToken: String, id: String, body: AddProductRequest): Flow<Resource<AddProductRequest>>
    suspend fun deleteProducts(storeToken: String, id: String): Flow<Resource<Unit>>
    suspend fun deleteLocation(storeToken: String, id: String): Flow<Resource<Unit>>
    suspend fun addStoreLocation(storeToken: String, body: AddLocationBody): Flow<Resource<Unit>>
    suspend fun getStorePayments(storeToken: String, body: GetPaymentBody): Flow<Resource<List<StorePayment>>>
    suspend fun updateStoreLocation(storeToken: String, id: String, body: AddLocationBody): Flow<Resource<Unit>>
    suspend fun createStore(type: String, token: String, body: CreateStoreBody): Flow<Resource<CreateStoreResponse>>
    suspend fun createStoreOnline(type: String,token: String, body: CreateStoreBody): Flow<Resource<OnlinePayment>>
    suspend fun getStoreToken(f_id: String): Flow<Resource<StoreTokenEntity>>
}