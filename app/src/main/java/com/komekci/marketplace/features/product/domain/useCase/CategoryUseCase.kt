package com.komekci.marketplace.features.product.domain.useCase

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.home.data.entity.FilterRequest
import com.komekci.marketplace.features.home.data.entity.GetFilter
import com.komekci.marketplace.features.product.data.entity.ProductRequest
import com.komekci.marketplace.features.product.domain.model.CategoryEntity
import com.komekci.marketplace.features.product.domain.model.ProductsEntity
import com.komekci.marketplace.features.product.domain.model.StoreProductRequest
import com.komekci.marketplace.features.product.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(request: StoreProductRequest): Flow<Resource<List<CategoryEntity>>> {
        return categoryRepository.getCategories(request)
    }

    suspend fun getProducts(request: ProductRequest): Flow<Resource<List<ProductsEntity>>> {
        return categoryRepository.getProducts(request)
    }

    suspend fun getProductById(token: String, id: String): Flow<Resource<ProductsEntity>> {
        return categoryRepository.getProductById(token, id)
    }

    suspend fun getFilters(request: FilterRequest): Flow<Resource<GetFilter>> {
        return categoryRepository.getFilters(request)
    }
}