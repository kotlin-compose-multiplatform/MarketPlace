package com.komekci.marketplace.features.favorite.domain.usecase

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.favorite.data.entity.product.FavProductItem
import com.komekci.marketplace.features.favorite.data.entity.product.FavRequest
import com.komekci.marketplace.features.favorite.data.entity.store.FavStoreRequest
import com.komekci.marketplace.features.favorite.data.entity.store.FavoriteStore
import com.komekci.marketplace.features.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class FavoriteUseCase(
    private val repository: FavoriteRepository
) {
    suspend fun getFavoriteProducts(token: String): Flow<Resource<List<FavProductItem>>> {
        return repository.getFavoriteProducts(token)
    }
    suspend fun likeProduct(token: String, body: FavRequest): Flow<Resource<FavRequest>> {
        return repository.likeProduct(token, body)
    }
    suspend fun likeStore(token: String, body: FavStoreRequest): Flow<Resource<FavStoreRequest>> {
        return repository.likeStore(token, body)
    }
    suspend fun getFavoriteStores(token: String): Flow<Resource<List<FavoriteStore>>> {
        return repository.getFavoriteStores(token)
    }
}