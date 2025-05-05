package com.komekci.marketplace.features.home.data.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.core.utils.ErrorExtractor
import com.komekci.marketplace.features.home.data.api.HomeApi
import com.komekci.marketplace.features.home.data.entity.country.CountryProductEntity
import com.komekci.marketplace.features.home.data.entity.country.CountryProductEntityItem
import com.komekci.marketplace.features.home.data.entity.country.CountryRequest
import com.komekci.marketplace.features.home.domain.model.CategoryEntity
import com.komekci.marketplace.features.home.domain.model.SearchRequest
import com.komekci.marketplace.features.home.domain.repository.CategoryRepository
import com.komekci.marketplace.features.product.domain.model.ProductsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoryRepositoryImpl(
    private val api: HomeApi
) : CategoryRepository {
    override suspend fun getCategories(): Flow<Resource<List<CategoryEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.getCategories()
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()?.category?.map { it.toUIEntity() }))
            } else {
                emit(
                    Resource.Error(
                        result.message(),
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun searchProducts(request: SearchRequest): Flow<Resource<List<ProductsEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.searchProducts(
                token = request.token,
                product = request.product,
                category = request.category,
                store = request.store,
                language = request.language
            )
            if(result.isSuccessful) {
                emit(Resource.Success(result.body()?.products?.map { it.toUiEntity() }))
            } else {
                emit(
                    Resource.Error(
                        result.message(),
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun getHomeProducts(request: CountryRequest): Flow<Resource<CountryProductEntity>> = flow {
        emit(Resource.Loading())
        try {
            val query = mapOf(
                "countryId" to request.countryId,
                "regionId" to request.regionId,
            )
            val result = api.getHome(query)
            if(result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(
                    Resource.Error(
                        result.message(),
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }
}