package com.komekci.marketplace.features.profile.data.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.core.utils.ErrorExtractor
import com.komekci.marketplace.features.create_store.data.entity.store.StoreOrderStatus
import com.komekci.marketplace.features.profile.data.api.ProfileApi
import com.komekci.marketplace.features.profile.data.entity.AddressResponse
import com.komekci.marketplace.features.profile.data.entity.CreateAddressRequest
import com.komekci.marketplace.features.profile.data.entity.SecondProfileEntity
import com.komekci.marketplace.features.profile.data.entity.UpdateUser
import com.komekci.marketplace.features.profile.data.entity.UpdateUserResponse
import com.komekci.marketplace.features.profile.data.entity.order.OrderResponseItem
import com.komekci.marketplace.features.profile.data.entity.order.SingleOrder
import com.komekci.marketplace.features.profile.data.entity.order.StoreOrderRequest
import com.komekci.marketplace.features.profile.data.entity.payments.GetPaymentBody
import com.komekci.marketplace.features.profile.data.entity.payments.PayBody
import com.komekci.marketplace.features.profile.data.entity.payments.Payment
import com.komekci.marketplace.features.profile.data.entity.payments.PaymentHistory
import com.komekci.marketplace.features.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody


class ProfileRepositoryImpl(
    private val api: ProfileApi
) : ProfileRepository {
    override suspend fun addAddress(
        body: CreateAddressRequest,
        token: String
    ): Flow<Resource<AddressResponse?>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.addAddress(token, body)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(
                    Resource.Error(
                        null,
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

    override suspend fun updateAddress(
        body: CreateAddressRequest,
        id: String,
        token: String
    ): Flow<Resource<AddressResponse?>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.updateAddress(token, id, body)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(
                    Resource.Error(
                        null,
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

    override suspend fun deleteAddress(id: String, token: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.deleteAddress(token, id)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(
                    Resource.Error(
                        null,
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

    override suspend fun getAddress(token: String): Flow<Resource<List<AddressResponse>?>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.getAddress(token)
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

    override suspend fun getProfile(token: String): Flow<Resource<SecondProfileEntity?>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.getProfile(token)
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

    override suspend fun updateUser(
        token: String,
        body: UpdateUser
    ): Flow<Resource<UpdateUserResponse?>> = flow {
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
            val countryId =
                RequestBody.create(MultipartBody.FORM, body.countryId?.toString()?:"")
            val regionId =
                RequestBody.create(MultipartBody.FORM, body.regionId?.toString()?:"")
            val result = api.updateUser(
                token = token,
                name = fullName,
                phoneNumber = phoneNumber,
                countryId = countryId,
                regionId = regionId,
                image = image
            )
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

    override suspend fun getMyOrders(token: String, guestId: String, isLoggedIn: Boolean): Flow<Resource<List<OrderResponseItem>>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = api.getMyOrders(guestId,token)
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

    override suspend fun getStoreOrders(
        token: String,
        request: StoreOrderRequest
    ): Flow<Resource<List<OrderResponseItem>>> = flow {
        emit(Resource.Loading())
        try {
            val queries = LinkedHashMap<String, Any>()
            if (request.date != null) {
                queries["date"] = request.date
            }
            if (request.status != null) {
                queries["status"] = request.status
            }
            val result = api.getStoreOrders(token, queries)
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

    override suspend fun updateStoreOrder(
        token: String,
        orderId: String,
        request: StoreOrderStatus
    ): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.updateStoreOrderStatus(token, orderId, request)
            if(result.isSuccessful) {
                emit(Resource.Success(true))
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

    override suspend fun getPaymentHistory(
        token: String,
        body: GetPaymentBody
    ): Flow<Resource<List<Payment>>> = flow {
        emit(Resource.Loading())
        try {
            val queries = LinkedHashMap<String, Any>()
            if (body.history != null) {
                queries["history"] = body.history
            }
            if (body.date != null) {
                queries["date"] = body.date
            }
            val result = api.getPaymentHistory(token, queries)
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

    override suspend fun payWithKey(token: String, body: PayBody): Flow<Resource<PaymentHistory>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.payWithKey(token, body)
            if (result.isSuccessful && result.body()?.payment!=null) {
                emit(Resource.Success(result.body()))
            } else {
                emit(
                    Resource.Error(
                        "Invalid Key",
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

    override suspend fun deleteUserImage(token: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.deleteUserImage(token)
            if (result.isSuccessful) {
                emit(Resource.Success("Success"))
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

    override suspend fun getSingleOrder(token: String, id: String, guestId: String): Flow<Resource<SingleOrder>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.getSingleOrder(guestId,token,id)
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

    override suspend fun getStoreSingleOrder(
        token: String,
        id: String
    ): Flow<Resource<SingleOrder>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.getStoreSingleOrder(token,id)
            println("[STORE_SINGLE_ORDER] SUCCESS ORDER")
            if (result.isSuccessful) {
                println("[STORE_SINGLE_ORDER] SUCCESS ORDER 2")
                emit(Resource.Success(result.body()))
            } else {
                println("[STORE_SINGLE_ORDER] ${result.code()}")
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
            println("[STORE_SINGLE_ORDER] ${ex.localizedMessage}")
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }
}