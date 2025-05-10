package com.komekci.marketplace.features.auth.data.repository

import android.util.Log
import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.core.utils.ErrorExtractor
import com.komekci.marketplace.features.auth.data.api.AuthApi
import com.komekci.marketplace.features.auth.data.entity.CheckCodeEntity
import com.komekci.marketplace.features.auth.data.entity.CheckCodeRequest
import com.komekci.marketplace.features.auth.data.entity.LoginRequest
import com.komekci.marketplace.features.auth.data.entity.LoginResponse
import com.komekci.marketplace.features.auth.data.entity.RegisterRequest
import com.komekci.marketplace.features.auth.data.entity.RegisterResponse
import com.komekci.marketplace.features.auth.data.entity.SendCodeRequest
import com.komekci.marketplace.features.auth.data.entity.country.AllCountryEntityItem
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val userDataStore: UserDataStore
) : AuthRepository {
    override suspend fun register(body: RegisterRequest): Flow<Resource<RegisterResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.registerUser(body)
            if (response.isSuccessful) {
                response.body()?.let {
//                    val isSent = api.sendCode(SendCodeRequest(it.userId))
//                    if (isSent.isSuccessful.not()) {
//                        emit(Resource.Error("Sent code error"))
//                    } else {
                        emit(Resource.Success(response.body()))
//                    }
                }
                if (response.body() == null) {
                    emit(Resource.Error("Response body is empty"))
                }
            } else {
                val errorMessage = ErrorExtractor.extract(response.errorBody())
                emit(Resource.Error("", errorMessage))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage))
        }
    }

    override suspend fun sendCode(userId: Int): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.sendCode(SendCodeRequest(userId))
            emit(Resource.Success(response.isSuccessful))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage))
        }
    }

    override suspend fun checkCode(userId: Int, code: String): Flow<Resource<CheckCodeEntity>> =
        flow {
            emit(Resource.Loading())

            try {
                val response = api.checkCode(CheckCodeRequest(userId, code))
                if (response.isSuccessful) {
                    emit(Resource.Success(response.body()))
                } else {
                    emit(Resource.Error("", ErrorExtractor.extract(response.errorBody())))
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                emit(Resource.Error(ex.localizedMessage))
            }
        }

    override suspend fun login(body: LoginRequest): Flow<Resource<LoginResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.login(body)
            if (response.isSuccessful) {
                response.body()?.let {
                    val isSent = api.sendCode(SendCodeRequest(it.userId))
                    if (isSent.isSuccessful.not()) {
                        emit(Resource.Error("Sent code error"))
                    } else {
                        emit(Resource.Success(response.body()))
                    }
                }
                if (response.body() == null) {
                    emit(Resource.Error("Response body is empty"))
                }
            } else {
                val errorMessage = ErrorExtractor.extract(response.errorBody())
                emit(Resource.Error("", errorMessage))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage))
        }
    }

    override suspend fun getAllCountries(): Flow<Resource<List<AllCountryEntityItem>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getAllCountries()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(response.body()))
                }
                if (response.body() == null) {
                    emit(Resource.Error("Response body is empty"))
                }
            } else {
                val errorMessage = ErrorExtractor.extract(response.errorBody())
                emit(Resource.Error("", errorMessage))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage))
        }
    }

    override suspend fun getGuestId(): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        val cachedId = userDataStore.getGuestId()
        if(cachedId.trim().isNotEmpty()) {
            Log.e("GUEST ID", "FROM LOCAL: ${cachedId}")
            emit(Resource.Success(cachedId))
        } else {
            try {
                val response = api.getGuestId()
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        Log.e("GUEST ID", "FROM REMOTE: ${body.guestId}")
                        userDataStore.saveGuestId(body.guestId?:"")
                        emit(Resource.Success(body.guestId))
                    }
                    if (response.body() == null) {
                        Log.e("GUEST ID", "FROM ERROR ${response.code()}")
                        emit(Resource.Error("Response body is empty"))
                    }
                } else {
                    Log.e("GUEST ID", "FROM ERROR ${response.errorBody()}")
                    val errorMessage = ErrorExtractor.extract(response.errorBody())
                    emit(Resource.Error("", errorMessage))
                }
            } catch (ex: Exception) {
                Log.e("GUEST ID", "FROM ERROR ${ex.localizedMessage}")
                ex.printStackTrace()
                emit(Resource.Error(ex.localizedMessage))
            }
        }
    }
}
