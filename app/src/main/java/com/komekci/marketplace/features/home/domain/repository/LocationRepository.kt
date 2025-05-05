package com.komekci.marketplace.features.home.domain.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.home.domain.model.LocationEntity
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getLocations(region: Boolean = false): Flow<Resource<List<LocationEntity>>>
}