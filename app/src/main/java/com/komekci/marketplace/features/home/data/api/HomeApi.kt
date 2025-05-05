package com.komekci.marketplace.features.home.data.api

import com.komekci.marketplace.features.home.data.entity.CategoryApiEntity
import com.komekci.marketplace.features.home.data.entity.ShopApiEntity
import com.komekci.marketplace.features.home.data.entity.StoresApiEntity
import com.komekci.marketplace.features.home.data.entity.country.CountryProductEntity
import com.komekci.marketplace.features.home.data.entity.country.CountryProductEntityItem
import com.komekci.marketplace.features.home.data.entity.discount.DiscountStoresItem
import com.komekci.marketplace.features.home.data.entity.location.CountryLocationEntityItem
import com.komekci.marketplace.features.home.data.entity.location.LocationApiEntityItem
import com.komekci.marketplace.features.home.data.entity.location.LocationRegionApiEntityItem
import com.komekci.marketplace.features.home.data.entity.search.SearchApiEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface HomeApi {
    @GET("client/stores")
    suspend fun getStores(): Response<StoresApiEntity?>

    @GET("client/vip-stores")
    suspend fun getVipStores(): Response<List<ShopApiEntity>?>

    @GET("client/categories")
    suspend fun getCategories(): Response<CategoryApiEntity?>

    @GET("client/locations")
    suspend fun getRegions(): Response<List<LocationRegionApiEntityItem>?>

    @GET("client/country/locations")
    suspend fun getLocations(): Response<List<LocationApiEntityItem>?>

    @GET("client/country/locations")
    suspend fun getCountryLocations(): Response<List<CountryLocationEntityItem>?>

    @GET("client/home?")
    suspend fun getHome(
        @QueryMap query: Map<String, String>
    ): Response<CountryProductEntity?>

    @GET("client/discount-stores")
    suspend fun getDiscountStores(
        @Header("Authorization") token: String
    ): Response<List<DiscountStoresItem>?>

    @GET("client/search?")
    suspend fun searchProducts(
        @Header("Authorization") token: String,
        @Header("Accept-Language") language: String,
        @Query("product") product: String,
        @Query("category") category: String,
        @Query("store") store: String,
    ): Response<SearchApiEntity>




}