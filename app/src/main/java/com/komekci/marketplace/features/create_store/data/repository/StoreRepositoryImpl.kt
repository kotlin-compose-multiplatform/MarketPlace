package com.komekci.marketplace.features.create_store.data.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.core.utils.ErrorExtractor
import com.komekci.marketplace.features.create_store.data.api.StoreApi
import com.komekci.marketplace.features.create_store.data.entity.add_product.AddProductRequest
import com.komekci.marketplace.features.create_store.data.entity.add_product.SingleProduct
import com.komekci.marketplace.features.create_store.data.entity.add_product.UploadProductImage
import com.komekci.marketplace.features.create_store.data.entity.edit.EditStoreResponse
import com.komekci.marketplace.features.create_store.data.entity.edit.UpdateStore
import com.komekci.marketplace.features.create_store.data.entity.edit.UpdateStoreBody
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
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class StoreRepositoryImpl(
    private val api: StoreApi
) : StoreRepository {
    override suspend fun getMyStore(storeToken: String): Flow<Resource<MyStore>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.getMyStore(storeToken)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(
                    Resource.Error(
                        "",
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun updateStore(
        storeToken: String,
        body: UpdateStore
    ): Flow<Resource<EditStoreResponse>> = flow {
        emit(Resource.Loading())
        try {
            val requestFile: RequestBody? = body.image?.let {
                RequestBody.create("image".toMediaTypeOrNull(), it)
            }
            val image: MultipartBody.Part? = body.image?.let {
                MultipartBody.Part.createFormData(
                    "image",
                    body.image.name,
                    requestFile!!
                )
            }
            val fullName =
                RequestBody.create(MultipartBody.FORM, body.name)
            val phoneNumber =
                RequestBody.create(MultipartBody.FORM, body.phone)
            val tiktok =
                RequestBody.create(MultipartBody.FORM, body.tiktok)
            val instagram =
                RequestBody.create(MultipartBody.FORM, body.instagram)
            val result = if (image != null) {
                api.updateStoreWithImage(
                    token = storeToken,
                    name = fullName,
                    phoneNumber = phoneNumber,
                    image = image,
                    tiktok = tiktok,
                    instagram = instagram
                )
            } else {
                api.updateStore(
                    token = storeToken,
                    body = UpdateStoreBody(body.name, body.phone, body.instagram, body.tiktok)
                )
            }
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(
                    Resource.Error(
                        "Something went wrong",
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun getMyProducts(storeToken: String): Flow<Resource<List<MyProductsItem>>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = api.getMyProducts(storeToken)
                if (result.isSuccessful) {
                    emit(Resource.Success(result.body()))
                } else {
                    emit(
                        Resource.Error(
                            "",
                            ErrorExtractor.extract(result.errorBody()),
                            result.code()
                        )
                    )
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                emit(Resource.Error(ex.localizedMessage, null, 500))
            }
        }

    override suspend fun getMyProductById(
        storeToken: String,
        productId: Int
    ): Flow<Resource<SingleProduct>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.getMyProductById(storeToken, productId)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(
                    Resource.Error(
                        "",
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun getParams(
        storeToken: String,
        body: ParamRequest
    ): Flow<Resource<ProductParams>> = flow {
        emit(Resource.Loading())
        try {
            val queries = LinkedHashMap<String, Any>()
            if (body.catalogId != null) {
                queries["catalogId"] = body.catalogId
            }
            if (body.categoryId != null) {
                queries["categoryId"] = body.categoryId
            }
            var result = ProductParams()

            val brands = api.getBrands(storeToken, queries)
            if (brands.isSuccessful && brands.body().isNullOrEmpty().not()) {
                result = result.copy(
                    brands = brands.body()
                )
            }
            val categories = api.getCategories(storeToken, queries)
            if (categories.isSuccessful && categories.body().isNullOrEmpty().not()) {
                result = result.copy(
                    categories = categories.body()
                )
            }
            val catalogs = api.getCatalogs(storeToken, queries)
            if (catalogs.isSuccessful && catalogs.body().isNullOrEmpty().not()) {
                result = result.copy(
                    catalogs = catalogs.body()
                )
            }
            val subCatalogs = api.getSubCatalogs(storeToken, queries)
            if (subCatalogs.isSuccessful && subCatalogs.body().isNullOrEmpty().not()) {
                result = result.copy(
                    subCatalogs = subCatalogs.body()
                )
            }
            emit(Resource.Success(result))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun uploadProductImage(
        storeToken: String,
        files: List<File>,
        onDone: (List<UploadProductImage>) -> Unit
    ): Flow<Resource<UploadProductImage>> = flow {
        emit(Resource.Loading())
        try {
            var list: List<UploadProductImage> = emptyList()
            files.forEachIndexed { index, file ->
                val requestFile: RequestBody = file.let {
                    RequestBody.create("image".toMediaTypeOrNull(), it)
                }
                val image: MultipartBody.Part = file.let {
                    MultipartBody.Part.createFormData(
                        "image",
                        file.name,
                        requestFile
                    )
                }
                val result = api.uploadProductImage(storeToken, image)
                if (result.isSuccessful) {
                    val res = result.body()?.copy(index = index)
                    res?.let {
                        list = list.plus(res)
                    }
                    emit(Resource.Success(res))
                } else {
                    val res = UploadProductImage(-1, "", index)
                    list = list.plus(res)
                    emit(
                        Resource.Error(
                            result.message(),
                            ErrorExtractor.extract(result.errorBody()),
                            result.code(),
                            res
                        )
                    )
                }
            }
            onDone(list)
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun addProduct(
        storeToken: String,
        body: AddProductRequest
    ): Flow<Resource<AddProductRequest>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.addProduct(storeToken, body)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(
                    Resource.Error(
                        "",
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun editProduct(
        storeToken: String,
        id: String,
        body: AddProductRequest
    ): Flow<Resource<AddProductRequest>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.editProduct(storeToken, id, body)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(
                    Resource.Error(
                        "",
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun deleteProducts(storeToken: String, id: String): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = api.deleteProduct(storeToken, id)
                if (result.isSuccessful) {
                    emit(Resource.Success(result.body()))
                } else {
                    emit(
                        Resource.Error(
                            "",
                            ErrorExtractor.extract(result.errorBody()),
                            result.code()
                        )
                    )
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                emit(Resource.Error(ex.localizedMessage, null, 500))
            }
        }

    override suspend fun deleteLocation(storeToken: String, id: String): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = api.deleteLocation(storeToken, id)
                if (result.isSuccessful) {
                    emit(Resource.Success(result.body()))
                } else {
                    emit(
                        Resource.Error(
                            "",
                            ErrorExtractor.extract(result.errorBody()),
                            result.code()
                        )
                    )
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                emit(Resource.Error(ex.localizedMessage, null, 500))
            }
        }

    override suspend fun addStoreLocation(
        storeToken: String,
        body: AddLocationBody
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.addStoreLocation(storeToken, body)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(
                    Resource.Error(
                        "",
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun getStorePayments(
        storeToken: String,
        body: GetPaymentBody
    ): Flow<Resource<List<StorePayment>>> = flow {
        emit(Resource.Loading())
        try {
            val queries = LinkedHashMap<String, Any>()
//            if (body.history != null) {
//                queries["history"] = body.history
//            }
            if (body.date != null) {
                queries["date"] = body.date
            }
            val result = api.getStorePayments(storeToken, queries)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(
                    Resource.Error(
                        "",
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun updateStoreLocation(
        storeToken: String,
        id: String,
        body: AddLocationBody
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.updateStoreLocation(storeToken, id, body)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(
                    Resource.Error(
                        "",
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun createStore(
        type: String,
        token: String,
        body: CreateStoreBody
    ): Flow<Resource<CreateStoreResponse>> = flow {
        emit(Resource.Loading())
        try {
            val result =
                if (type == "NEW") api.createStore(token, body) else api.payStore(token, body)
            if (result.isSuccessful) {
                if (type == "NEW")
                    emit(Resource.Success(result.body()))
                else
                    emit(Resource.Success(CreateStoreResponse(storeToken = token)))
            } else {
                emit(
                    Resource.Error(
                        "",
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun createStoreOnline(
        type: String,
        token: String,
        body: CreateStoreBody
    ): Flow<Resource<OnlinePayment>> = flow {
        emit(Resource.Loading())
        try {
            val result =
                if (type == "NEW") api.createStoreOnline(token, body) else api.payStoreOnline(
                    token,
                    body
                )
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {

                emit(
                    Resource.Error(
                        "",
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun getStoreToken(f_id: String): Flow<Resource<StoreTokenEntity>> = flow {
        emit(Resource.Loading())
        try {
            val queries = mapOf(
                "orderId" to f_id
            )
            val response = api.getStoreToken(queries)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()))
            } else {
                emit(
                    Resource.Error(
                        "",
                        ErrorExtractor.extract(response.errorBody()),
                        response.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }
}