package com.komekci.marketplace.features.product.data.api

import com.komekci.marketplace.features.home.data.entity.GetFilter
import com.komekci.marketplace.features.home.data.entity.store.StoreProducts
import com.komekci.marketplace.features.product.data.entity.ProductApiEntity
import com.komekci.marketplace.features.product.data.entity.SingleProductEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ProductApi {
    @GET("client/products?")
    suspend fun getProducts(
        @Header("Authorization") token: String,
        @QueryMap queries: LinkedHashMap<String, Any>
    ): Response<ProductApiEntity?>

    @GET("client/products/{id}")
    suspend fun getProductById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<SingleProductEntity?>

    @GET("client/filters")
    suspend fun getFilters(
        @QueryMap queries: LinkedHashMap<String, Any>
    ): Response<GetFilter?>

    @GET("client/store/{id}/products?")
    suspend fun getStoreProducts(
        @Header("Authorization") token: String,
        @Path("id") storeId: String,
        @QueryMap queries: LinkedHashMap<String, Any>
    ): Response<List<StoreProducts>?>
}