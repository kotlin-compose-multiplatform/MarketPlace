package com.komekci.marketplace.features.basket.domain.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.basket.data.entity.BasketRequest
import com.komekci.marketplace.features.basket.data.entity.response.BasketResponse
import kotlinx.coroutines.flow.Flow

interface BasketRepository {
    suspend fun sendOrder(
        token: String,
        body: BasketRequest
    ): Flow<Resource<BasketResponse>>
}