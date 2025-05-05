package com.komekci.marketplace.features.home.domain.usecase

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.home.data.entity.country.CountryProductEntity
import com.komekci.marketplace.features.home.data.entity.country.CountryProductEntityItem
import com.komekci.marketplace.features.home.data.entity.country.CountryRequest
import com.komekci.marketplace.features.home.domain.model.CategoryEntity
import com.komekci.marketplace.features.home.domain.model.SearchRequest
import com.komekci.marketplace.features.home.domain.repository.CategoryRepository
import com.komekci.marketplace.features.product.domain.model.ProductsEntity
import kotlinx.coroutines.flow.Flow

class CategoryUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<CategoryEntity>>> {
        return repository.getCategories()
    }
    suspend fun searchProducts(request: SearchRequest): Flow<Resource<List<ProductsEntity>>> {
        return repository.searchProducts(request)
    }
    suspend fun getHomeProducts(request: CountryRequest): Flow<Resource<CountryProductEntity>> {
        return repository.getHomeProducts(request)
    }
}