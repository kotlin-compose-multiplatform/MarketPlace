package com.komekci.marketplace.features.auth.data.api

import com.komekci.marketplace.features.auth.data.entity.CheckCodeEntity
import com.komekci.marketplace.features.auth.data.entity.CheckCodeRequest
import com.komekci.marketplace.features.auth.data.entity.GuestIdEntity
import com.komekci.marketplace.features.auth.data.entity.LoginRequest
import com.komekci.marketplace.features.auth.data.entity.LoginResponse
import com.komekci.marketplace.features.auth.data.entity.RegisterRequest
import com.komekci.marketplace.features.auth.data.entity.RegisterResponse
import com.komekci.marketplace.features.auth.data.entity.SendCodeRequest
import com.komekci.marketplace.features.auth.data.entity.SendCodeResponse
import com.komekci.marketplace.features.auth.data.entity.country.AllCountryEntityItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("client/auth/register/user")
    suspend fun registerUser(
        @Body body: RegisterRequest
    ): Response<RegisterResponse?>

    @POST("client/auth/send-code")
    suspend fun sendCode(
        @Body body: SendCodeRequest
    ): Response<SendCodeResponse?>

    @POST("client/auth/check-code")
    suspend fun checkCode(
        @Body body: CheckCodeRequest
    ): Response<CheckCodeEntity?>

    @POST("client/auth/login/user")
    suspend fun login(
        @Body body: LoginRequest
    ): Response<LoginResponse?>

    @GET("client/country/locations")
    suspend fun getAllCountries(): Response<List<AllCountryEntityItem>>

    @GET("client/guest")
    suspend fun getGuestId(): Response<GuestIdEntity?>

}