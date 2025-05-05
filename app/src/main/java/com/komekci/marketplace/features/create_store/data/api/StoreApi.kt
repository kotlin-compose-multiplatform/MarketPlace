package com.komekci.marketplace.features.create_store.data.api

import com.komekci.marketplace.features.create_store.data.entity.add_product.AddProductRequest
import com.komekci.marketplace.features.create_store.data.entity.add_product.SingleProduct
import com.komekci.marketplace.features.create_store.data.entity.add_product.UploadProductImage
import com.komekci.marketplace.features.create_store.data.entity.edit.EditStoreResponse
import com.komekci.marketplace.features.create_store.data.entity.edit.UpdateStoreBody
import com.komekci.marketplace.features.create_store.data.entity.location.AddLocationBody
import com.komekci.marketplace.features.create_store.data.entity.online.OnlinePayment
import com.komekci.marketplace.features.create_store.data.entity.online.StoreTokenEntity
import com.komekci.marketplace.features.create_store.data.entity.params.GetBrandItem
import com.komekci.marketplace.features.create_store.data.entity.params.GetCatalogItem
import com.komekci.marketplace.features.create_store.data.entity.params.GetCategoryItem
import com.komekci.marketplace.features.create_store.data.entity.params.GetSubCatalogItem
import com.komekci.marketplace.features.create_store.data.entity.payment.StorePayment
import com.komekci.marketplace.features.create_store.data.entity.product.MyProductsItem
import com.komekci.marketplace.features.create_store.data.entity.store.CreateStoreBody
import com.komekci.marketplace.features.create_store.data.entity.store.CreateStoreResponse
import com.komekci.marketplace.features.create_store.data.entity.store.MyStore
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

interface StoreApi {
    @GET("admin/store/")
    suspend fun getMyStore(
        @Header("store-authorization") storeToken: String
    ): Response<MyStore?>

    @Multipart
    @PUT("admin/store/")
    suspend fun updateStoreWithImage(
        @Header("store-authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part("instagram") instagram: RequestBody,
        @Part("tiktok") tiktok: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<EditStoreResponse?>

    @PUT("admin/store/")
    suspend fun updateStore(
        @Header("store-authorization") token: String,
        @Body body: UpdateStoreBody
    ): Response<EditStoreResponse?>

    @GET("admin/products/")
    suspend fun getMyProducts(
        @Header("store-authorization") token: String,
    ): Response<List<MyProductsItem>?>

    @GET("admin/products/{productId}")
    suspend fun getMyProductById(
        @Header("store-authorization") token: String,
        @Path("productId") productId: Int
    ): Response<SingleProduct?>

    @GET("superadmin/catalog/?")
    suspend fun getCatalogs(
        @Header("store-authorization") token: String,
        @QueryMap queries: LinkedHashMap<String, Any>
    ): Response<List<GetCatalogItem>?>

    @GET("superadmin/category/?")
    suspend fun getCategories(
        @Header("store-authorization") token: String,
        @QueryMap queries: LinkedHashMap<String, Any>
    ): Response<List<GetCategoryItem>?>

    @GET("superadmin/subcatalog/?")
    suspend fun getSubCatalogs(
        @Header("store-authorization") token: String,
        @QueryMap queries: LinkedHashMap<String, Any>
    ): Response<List<GetSubCatalogItem>?>

    @GET("superadmin/brand/?")
    suspend fun getBrands(
        @Header("store-authorization") token: String,
        @QueryMap queries: LinkedHashMap<String, Any>
    ): Response<List<GetBrandItem>?>

    @Multipart
    @POST("admin/products/upload-image")
    suspend fun uploadProductImage(
        @Header("store-authorization") token: String,
        @Part image: MultipartBody.Part?
    ): Response<UploadProductImage?>

    @POST("admin/products/")
    suspend fun addProduct(
        @Header("store-authorization") token: String,
        @Body body: AddProductRequest
    ): Response<AddProductRequest?>

    @PUT("admin/products/{id}")
    suspend fun editProduct(
        @Header("store-authorization") token: String,
        @Path("id") id: String,
        @Body body: AddProductRequest
    ): Response<AddProductRequest?>

    @DELETE("admin/products/{id}")
    suspend fun deleteProduct(
        @Header("store-authorization") token: String,
        @Path("id") id: String
    ): Response<Unit>


    @DELETE("admin/store/location/{id}")
    suspend fun deleteLocation(
        @Header("store-authorization") token: String,
        @Path("id") id: String
    ): Response<Unit>
    @POST("admin/store/location")
    suspend fun addStoreLocation(
        @Header("store-authorization") token: String,
        @Body body: AddLocationBody
    ): Response<Unit>

    @PUT("admin/store/location/{id}")
    suspend fun updateStoreLocation(
        @Header("store-authorization") token: String,
        @Path("id") id: String,
        @Body body: AddLocationBody
    ): Response<Unit>

    @GET("admin/payment/store-history")
    suspend fun getStorePayments(
        @Header("store-authorization") token: String,
        @QueryMap queries: LinkedHashMap<String, Any>
    ): Response<List<StorePayment>?>

    @POST("client/auth/register/store")
    suspend fun createStore(
        @Header("authorization") token: String,
        @Body body: CreateStoreBody
    ): Response<CreateStoreResponse>

    @POST("client/auth/register/store")
    suspend fun createStoreOnline(
        @Header("authorization") token: String,
        @Body body: CreateStoreBody
    ): Response<OnlinePayment>

    @POST("admin/payment/pay-store-with-key")
    suspend fun payStore(
        @Header("store-authorization") token: String,
        @Body body: CreateStoreBody
    ): Response<CreateStoreResponse>

    @POST("admin/payment/pay-store-with-bank")
    suspend fun payStoreOnline(
        @Header("store-authorization") token: String,
        @Body body: CreateStoreBody
    ): Response<OnlinePayment>

    @GET("client/store-payment/payment/callback-handler?")
    suspend fun getStoreToken(
        @QueryMap queries: Map<String, String>
    ): Response<StoreTokenEntity>
}