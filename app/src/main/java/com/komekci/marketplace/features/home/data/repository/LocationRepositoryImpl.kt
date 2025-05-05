package com.komekci.marketplace.features.home.data.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.core.utils.ErrorExtractor
import com.komekci.marketplace.features.home.data.api.HomeApi
import com.komekci.marketplace.features.home.data.entity.location.LocationApiEntityItem
import com.komekci.marketplace.features.home.data.entity.location.LocationRegionApiEntityItem
import com.komekci.marketplace.features.home.domain.model.LocationEntity
import com.komekci.marketplace.features.home.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocationRepositoryImpl(
    private val api: HomeApi
) : LocationRepository {
    override suspend fun getLocations(region: Boolean): Flow<Resource<List<LocationEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val result = if(region) api.getRegions() else api.getLocations()
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()?.map { v->
                    if(region) (v as LocationRegionApiEntityItem).toUiEntity() else (v as LocationApiEntityItem).toUiEntity()
                }))
            } else {
                emit(
                    Resource.Error(
                        result.message(),
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