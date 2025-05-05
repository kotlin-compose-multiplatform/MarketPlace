package com.komekci.marketplace.features.home.domain.usecase

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.home.domain.model.DiscountEntity
import com.komekci.marketplace.features.home.domain.repository.DiscountRepository
import kotlinx.coroutines.flow.Flow

class DiscountUseCase(
    private val repository: DiscountRepository
) {
    suspend operator fun invoke(token: String): Flow<Resource<List<DiscountEntity>>> {
        return repository.getDiscounts(token)
    }
}