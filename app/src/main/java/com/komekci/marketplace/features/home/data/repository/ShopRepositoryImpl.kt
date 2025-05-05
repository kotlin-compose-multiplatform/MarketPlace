package com.komekci.marketplace.features.home.data.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.core.utils.ErrorExtractor
import com.komekci.marketplace.features.home.data.api.HomeApi
import com.komekci.marketplace.features.home.domain.model.ShopEntity
import com.komekci.marketplace.features.home.domain.repository.ShopsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ShopRepositoryImpl(
    private val api: HomeApi
) : ShopsRepository {
    override suspend fun getShops(): Flow<Resource<List<ShopEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getStores()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.stores?.map { it.toUiEntity() }))
            } else {
                emit(
                    Resource.Error(
                        "",
                        ErrorExtractor.extract(response.errorBody()),
                        response.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun getVipShops(): Flow<Resource<List<ShopEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getVipStores()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.map { it.toUiEntity() }))
            } else {
                emit(
                    Resource.Error(
                        "",
                        ErrorExtractor.extract(response.errorBody()),
                        response.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }
}