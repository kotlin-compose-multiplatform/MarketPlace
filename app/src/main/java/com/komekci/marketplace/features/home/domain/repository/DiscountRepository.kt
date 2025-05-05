package com.komekci.marketplace.features.home.domain.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.home.domain.model.DiscountEntity
import kotlinx.coroutines.flow.Flow

interface DiscountRepository {
    suspend fun getDiscounts(token: String): Flow<Resource<List<DiscountEntity>>>
}