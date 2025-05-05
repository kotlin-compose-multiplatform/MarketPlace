package com.komekci.marketplace.features.home.domain.usecase

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.home.domain.model.ShopEntity
import com.komekci.marketplace.features.home.domain.repository.ShopsRepository
import kotlinx.coroutines.flow.Flow

class ShopsUseCase(
    private val repo: ShopsRepository
) {
    suspend operator fun invoke() : Flow<Resource<List<ShopEntity>>> {
        return repo.getShops()
    }

    suspend fun getVipShops(): Flow<Resource<List<ShopEntity>>> {
        return repo.getVipShops()
    }
}