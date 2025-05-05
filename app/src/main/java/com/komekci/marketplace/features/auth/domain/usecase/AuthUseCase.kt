package com.komekci.marketplace.features.auth.domain.usecase

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.auth.data.entity.CheckCodeEntity
import com.komekci.marketplace.features.auth.data.entity.LoginRequest
import com.komekci.marketplace.features.auth.data.entity.LoginResponse
import com.komekci.marketplace.features.auth.data.entity.RegisterRequest
import com.komekci.marketplace.features.auth.data.entity.RegisterResponse
import com.komekci.marketplace.features.auth.data.entity.country.AllCountryEntityItem
import com.komekci.marketplace.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthUseCase(
    private val repository: AuthRepository
){
    suspend fun register(
        name: String,
        phoneNumber: String,
        email: String,
        regionId: Int?,
        countryId: Int?
    ): Flow<Resource<RegisterResponse>> {
        return repository.register(
            body = RegisterRequest(name, phoneNumber, email, regionId, countryId)
        )
    }

    suspend fun sendCode(userId: Int): Flow<Resource<Boolean>> {
        return repository.sendCode(userId)
    }

    suspend fun checkCode(userId: Int, code: String): Flow<Resource<CheckCodeEntity>> {
        return repository.checkCode(userId, code)
    }

    suspend fun login(phoneNumber: String): Flow<Resource<LoginResponse>> {
        return repository.login(
            body = LoginRequest(phoneNumber)
        )
    }
    suspend fun getAllCountries(): Flow<Resource<List<AllCountryEntityItem>>> {
        return repository.getAllCountries()
    }

    suspend fun getGuestId(): Flow<Resource<String>> {
        return repository.getGuestId()
    }
}