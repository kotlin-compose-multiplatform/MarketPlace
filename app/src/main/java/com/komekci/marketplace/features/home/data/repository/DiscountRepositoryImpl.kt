package com.komekci.marketplace.features.home.data.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.core.utils.ErrorExtractor
import com.komekci.marketplace.features.home.data.api.HomeApi
import com.komekci.marketplace.features.home.domain.model.DiscountEntity
import com.komekci.marketplace.features.home.domain.repository.DiscountRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DiscountRepositoryImpl(
    private val api: HomeApi
) : DiscountRepository {
    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun getDiscounts(token: String): Flow<Resource<List<DiscountEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.getDiscountStores(token)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()?.map { it.toUiEntity() }))
            } else {
                emit(
                    Resource.Error(
                        "",
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