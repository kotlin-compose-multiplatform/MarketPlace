package com.komekci.marketplace.features.favorite.domain.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.favorite.data.entity.product.FavProductItem
import com.komekci.marketplace.features.favorite.data.entity.product.FavRequest
import com.komekci.marketplace.features.favorite.data.entity.store.FavStoreRequest
import com.komekci.marketplace.features.favorite.data.entity.store.FavoriteStore
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun getFavoriteProducts(token: String): Flow<Resource<List<FavProductItem>>>
    suspend fun getFavoriteStores(token: String): Flow<Resource<List<FavoriteStore>>>
    suspend fun likeProduct(token: String, body: FavRequest): Flow<Resource<FavRequest>>
    suspend fun likeStore(token: String, body: FavStoreRequest): Flow<Resource<FavStoreRequest>>
}