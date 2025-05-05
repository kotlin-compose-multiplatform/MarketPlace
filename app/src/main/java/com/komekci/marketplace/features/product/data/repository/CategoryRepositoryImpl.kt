package com.komekci.marketplace.features.product.data.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.core.utils.ErrorExtractor
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.home.data.entity.FilterRequest
import com.komekci.marketplace.features.home.data.entity.GetFilter
import com.komekci.marketplace.features.product.data.api.ProductApi
import com.komekci.marketplace.features.product.data.entity.ProductRequest
import com.komekci.marketplace.features.product.domain.model.CategoryEntity
import com.komekci.marketplace.features.product.domain.model.ProductsEntity
import com.komekci.marketplace.features.product.domain.model.StoreProductRequest
import com.komekci.marketplace.features.product.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoryRepositoryImpl(
    private val api: ProductApi,
    private val userDataStore: UserDataStore
) : CategoryRepository {
    override suspend fun getCategories(request: StoreProductRequest): Flow<Resource<List<CategoryEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val queries = LinkedHashMap<String, Any>()
            if (request.region != null && request.district != null && request.region!="0" && request.district!="0") {
                queries["location"] = "${request.region}.${request.district}"
            }

            queries["limit"] = request.limit

            val data = api.getStoreProducts(
                token = request.token,
                storeId = request.storeId,
                queries = queries
            )
            if(data.isSuccessful) {
                emit(Resource.Success(data.body()?.map { it.toUIEntity() }))
            } else {
                emit(
                    Resource.Error(
                        data.message(),
                        ErrorExtractor.extract(data.errorBody()),
                        data.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun getProducts(request: ProductRequest): Flow<Resource<List<ProductsEntity>>> =
        flow {
            emit(Resource.Loading())
            val queries = LinkedHashMap<String, Any>()
            if (request.price_lte != null) {
                queries["price[lte]"] = request.price_lte
            }
            if (request.price_gte != null) {
                queries["price[gte]"] = request.price_gte
            }

            if (request.discount != null) {
                queries["discount"] = request.discount
            }

            if (request.storeId.isNotEmpty()) {
                queries["storeId"] = request.storeId
            }

            if (request.catalogId.isNotEmpty()) {
                queries["catalogId"] = request.catalogId
            }

            if (request.brandId.isNotEmpty()) {
                queries["brandId"] = request.brandId
            }

            if (request.categoryId.isNotEmpty()) {
                queries["categoryId"] = request.categoryId
            }

            if(userDataStore.getCountryId().isNullOrEmpty().not()) {
                queries["countryId"] = userDataStore.getCountryId()!!
            }

            if(userDataStore.getRegionId().isNullOrEmpty().not()) {
                queries["regionId"] = userDataStore.getRegionId()!!
            }

            if (request.region != null && request.district != null && request.region!="0" && request.district!="0") {
                queries["location"] = "${request.region}.${request.district}"
            }

            queries["size"] = request.size
            queries["page"] = request.page

            try {
                val result = api.getProducts(
                    token = request.token ?: "",
                    queries = queries
                )
                if (result.isSuccessful) {
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

    override suspend fun getFilters(request: FilterRequest): Flow<Resource<GetFilter>> = flow {
        emit(Resource.Loading())
        try {
            val queries = LinkedHashMap<String, Any>()
            request.categoryId?.let {
                queries["categoryId"] = it
            }
            request.catalogId?.let {
                queries["catalogId"] = it
            }
            val result = api.getFilters(
                queries = queries
            )
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
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

    override suspend fun getProductById(token: String, id: String): Flow<Resource<ProductsEntity>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = api.getProductById(
                    token,
                    id
                )
                if (result.isSuccessful) {
                    emit(Resource.Success(result.body()?.toUiEntity()))
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
}