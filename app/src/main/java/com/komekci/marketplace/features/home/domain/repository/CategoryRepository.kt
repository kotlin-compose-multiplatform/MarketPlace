package com.komekci.marketplace.features.home.domain.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.home.data.entity.country.CountryProductEntity
import com.komekci.marketplace.features.home.data.entity.country.CountryProductEntityItem
import com.komekci.marketplace.features.home.data.entity.country.CountryRequest
import com.komekci.marketplace.features.home.domain.model.CategoryEntity
import com.komekci.marketplace.features.home.domain.model.SearchRequest
import com.komekci.marketplace.features.product.domain.model.ProductsEntity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategories(): Flow<Resource<List<CategoryEntity>>>
    suspend fun searchProducts(request: SearchRequest): Flow<Resource<List<ProductsEntity>>>
    suspend fun getHomeProducts(request: CountryRequest): Flow<Resource<CountryProductEntity>>
}