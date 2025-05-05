package com.komekci.marketplace.features.basket.data.api

import com.komekci.marketplace.features.basket.data.entity.BasketRequest
import com.komekci.marketplace.features.basket.data.entity.BasketRequestGuest
import com.komekci.marketplace.features.basket.data.entity.response.BasketResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BasketApi {
    @POST("client/orders")
    suspend fun sendOrder(
        @Header("Authorization") token: String,
        @Body body: BasketRequest
    ): Response<BasketResponse?>

    @POST("client/orders")
    suspend fun sendOrderAsGuest(
        @Header("guestId") guestId: String,
        @Body body: BasketRequestGuest
    ): Response<BasketResponse?>

}