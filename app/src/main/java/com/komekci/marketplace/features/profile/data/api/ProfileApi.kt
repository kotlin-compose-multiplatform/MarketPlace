package com.komekci.marketplace.features.profile.data.api

import com.komekci.marketplace.features.create_store.data.entity.store.StoreOrderStatus
import com.komekci.marketplace.features.profile.data.entity.AddressResponse
import com.komekci.marketplace.features.profile.data.entity.CreateAddressRequest
import com.komekci.marketplace.features.profile.data.entity.PrivacyPolicyEntity
import com.komekci.marketplace.features.profile.data.entity.ProfileEntity
import com.komekci.marketplace.features.profile.data.entity.SecondProfileEntity
import com.komekci.marketplace.features.profile.data.entity.UpdateUserResponse
import com.komekci.marketplace.features.profile.data.entity.order.OrderResponseItem
import com.komekci.marketplace.features.profile.data.entity.order.SingleOrder
import com.komekci.marketplace.features.profile.data.entity.payments.PayBody
import com.komekci.marketplace.features.profile.data.entity.payments.Payment
import com.komekci.marketplace.features.profile.data.entity.payments.PaymentHistory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ProfileApi {
    @POST("client/user/address")
    suspend fun addAddress(
        @Header("Authorization") token: String,
        @Body body: CreateAddressRequest
    ): Response<AddressResponse?>

    @PUT("client/user/address/{id}")
    suspend fun updateAddress(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body body: CreateAddressRequest
    ): Response<AddressResponse?>

    @DELETE("client/user/address/{id}")
    suspend fun deleteAddress(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Response<Unit>

    @GET("client/user/address")
    suspend fun getAddress(
        @Header("Authorization") token: String,
    ): Response<List<AddressResponse>?>

    @GET("client/user")
    suspend fun getProfile(
        @Header("Authorization") token: String,
    ): Response<SecondProfileEntity?>

    @Multipart
    @PUT("client/user")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part("countryId") countryId: RequestBody,
        @Part("regionId") regionId: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<UpdateUserResponse?>

    @GET("client/orders")
    suspend fun getMyOrders(
        @Header("guestId") guestId: String,
        @Header("Authorization") token: String
    ): Response<List<OrderResponseItem>?>

    @GET("admin/orders?")
    suspend fun getStoreOrders(
        @Header("store-authorization") token: String,
        @QueryMap queries: LinkedHashMap<String, Any>
    ): Response<List<OrderResponseItem>?>

    @PUT("admin/orders/{id}")
    suspend fun updateStoreOrderStatus(
        @Header("store-authorization") token: String,
        @Path("id") orderId: String,
        @Body body: StoreOrderStatus
    ): Response<ResponseBody?>

    @GET("client/payment/komekchi-history?")
    suspend fun getPaymentHistory(
        @Header("Authorization") token: String,
        @QueryMap queries: LinkedHashMap<String, Any>
    ): Response<List<Payment>?>

    @POST("client/payment/komekchi-pay-with-key")
    suspend fun payWithKey(
        @Header("Authorization") token: String,
        @Body body: PayBody
    ): Response<PaymentHistory>

    @DELETE("client/user-image")
    suspend fun deleteUserImage(
        @Header("Authorization") token: String,
    ): Response<Unit>

    @GET("client/orders/{orderId}")
    suspend fun getSingleOrder(
        @Header("guestId") guestId: String,
        @Header("Authorization") token: String,
        @Path("orderId") orderId: String
    ): Response<SingleOrder?>

    @GET("admin/orders/{orderId}")
    suspend fun getStoreSingleOrder(
        @Header("store-authorization") token: String,
        @Path("orderId") orderId: String
    ): Response<SingleOrder?>

    @GET("client/privacy")
    suspend fun getPrivacyPolicy(): Response<PrivacyPolicyEntity?>
}