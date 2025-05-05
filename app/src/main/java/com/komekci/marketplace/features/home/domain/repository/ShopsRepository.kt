package com.komekci.marketplace.features.home.domain.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.home.domain.model.ShopEntity
import kotlinx.coroutines.flow.Flow

interface ShopsRepository {
    suspend fun getShops(): Flow<Resource<List<ShopEntity>>>
    suspend fun getVipShops(): Flow<Resource<List<ShopEntity>>>
}