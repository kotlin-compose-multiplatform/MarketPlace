package com.komekci.marketplace.features.auth.domain.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.auth.data.entity.CheckCodeEntity
import com.komekci.marketplace.features.auth.data.entity.LoginRequest
import com.komekci.marketplace.features.auth.data.entity.LoginResponse
import com.komekci.marketplace.features.auth.data.entity.RegisterRequest
import com.komekci.marketplace.features.auth.data.entity.RegisterResponse
import com.komekci.marketplace.features.auth.data.entity.country.AllCountryEntityItem
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(body: RegisterRequest): Flow<Resource<RegisterResponse>>
    suspend fun sendCode(userId: Int): Flow<Resource<Boolean>>
    suspend fun checkCode(userId: Int, code: String): Flow<Resource<CheckCodeEntity>>
    suspend fun login(body: LoginRequest): Flow<Resource<LoginResponse>>
    suspend fun getAllCountries(): Flow<Resource<List<AllCountryEntityItem>>>
    suspend fun getGuestId(): Flow<Resource<String>>
}