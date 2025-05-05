package com.komekci.marketplace.features.favorite.data.repository

import android.util.Log
import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.core.utils.ErrorExtractor
import com.komekci.marketplace.features.favorite.data.api.FavoriteApi
import com.komekci.marketplace.features.favorite.data.entity.product.FavProductItem
import com.komekci.marketplace.features.favorite.data.entity.product.FavRequest
import com.komekci.marketplace.features.favorite.data.entity.store.FavStoreRequest
import com.komekci.marketplace.features.favorite.data.entity.store.FavoriteStore
import com.komekci.marketplace.features.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteRepositoryImpl(
    private val api: FavoriteApi
) : FavoriteRepository {
    override suspend fun getFavoriteProducts(token: String): Flow<Resource<List<FavProductItem>>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = api.getFavoriteProducts(token)
                if (result.isSuccessful) {
                    Log.e("FAVS", result.body()?.products?.size?.toString()?:"----")
                    emit(Resource.Success(result.body()?.products))
                } else {
                    Log.e("FAVS", result.code().toString())
                    emit(
                        Resource.Error(
                            null,
                            ErrorExtractor.extract(result.errorBody()),
                            result.code()
                        )
                    )
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e("FAVS", ex.localizedMessage)
                emit(Resource.Error(ex.localizedMessage, null, 500))
            }
        }

    override suspend fun getFavoriteStores(token: String): Flow<Resource<List<FavoriteStore>>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = api.getFavorites(token)
                if (result.isSuccessful) {
                    emit(Resource.Success(result.body()?.stores))
                } else {
                    emit(
                        Resource.Error(
                            null,
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

    override suspend fun likeProduct(token: String, body: FavRequest): Flow<Resource<FavRequest>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = api.likeProduct(token, body)
                if (result.isSuccessful) {
                    emit(Resource.Success(result.body()))
                } else {
                    emit(
                        Resource.Error(
                            null,
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

    override suspend fun likeStore(
        token: String,
        body: FavStoreRequest
    ): Flow<Resource<FavStoreRequest>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.likeStore(token, body)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(
                    Resource.Error(
                        null,
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