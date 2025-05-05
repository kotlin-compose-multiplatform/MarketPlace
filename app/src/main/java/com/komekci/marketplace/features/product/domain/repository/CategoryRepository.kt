package com.komekci.marketplace.features.product.domain.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.home.data.entity.FilterRequest
import com.komekci.marketplace.features.home.data.entity.GetFilter
import com.komekci.marketplace.features.product.data.entity.ProductRequest
import com.komekci.marketplace.features.product.domain.model.CategoryEntity
import com.komekci.marketplace.features.product.domain.model.ProductsEntity
import com.komekci.marketplace.features.product.domain.model.StoreProductRequest
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategories(request: StoreProductRequest): Flow<Resource<List<CategoryEntity>>>
    suspend fun getProducts(request: ProductRequest): Flow<Resource<List<ProductsEntity>>>
    suspend fun getFilters(request: FilterRequest): Flow<Resource<GetFilter>>
    suspend fun getProductById(token: String, id: String): Flow<Resource<ProductsEntity>>

}