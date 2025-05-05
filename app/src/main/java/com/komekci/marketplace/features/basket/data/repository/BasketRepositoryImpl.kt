package com.komekci.marketplace.features.basket.data.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.core.utils.ErrorExtractor
import com.komekci.marketplace.features.basket.data.api.BasketApi
import com.komekci.marketplace.features.basket.data.entity.BasketRequest
import com.komekci.marketplace.features.basket.data.entity.response.BasketResponse
import com.komekci.marketplace.features.basket.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BasketRepositoryImpl(
    private val api: BasketApi
) : BasketRepository {
    override suspend fun sendOrder(
        token: String,
        body: BasketRequest
    ): Flow<Resource<BasketResponse>> = flow {
        emit(Resource.Loading())
        try {
            if(body.isAuth) {
                val result = api.sendOrder(token, body)
                if (result.isSuccessful) {
                    emit(Resource.Success(result.body()))
                } else {
                    emit(Resource.Error("", ErrorExtractor.extract(result.errorBody()), result.code()))
                }
            } else {
                val result = api.sendOrderAsGuest(body.guestId, body.toGuest())
                if (result.isSuccessful) {
                    emit(Resource.Success(result.body()))
                } else {
                    emit(Resource.Error("", ErrorExtractor.extract(result.errorBody()), result.code()))
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }
}