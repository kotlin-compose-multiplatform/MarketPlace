package com.komekci.marketplace.features.favorite.data.api

import com.komekci.marketplace.features.favorite.data.entity.product.FavRequest
import com.komekci.marketplace.features.favorite.data.entity.store.FavStoreRequest
import com.komekci.marketplace.features.favorite.data.entity.store.FavoriteProductRoot
import com.komekci.marketplace.features.favorite.data.entity.store.FavoriteStoreRoot
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FavoriteApi {
    @GET("client/my-liked-products")
    suspend fun getFavoriteProducts(
        @Header("Authorization") token: String
    ): Response<FavoriteProductRoot?>

    @POST("client/like-product")
    suspend fun likeProduct(
        @Header("Authorization") token: String,
        @Body body: FavRequest
    ): Response<FavRequest?>

    @POST("client/like-store")
    suspend fun likeStore(
        @Header("Authorization") token: String,
        @Body body: FavStoreRequest
    ): Response<FavStoreRequest?>

    @GET("client/my-liked-stores")
    suspend fun getFavorites(
        @Header("Authorization") token: String,
    ): Response<FavoriteStoreRoot?>
}