package com.komekci.marketplace.features.home.domain.usecase

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.home.domain.model.LocationEntity
import com.komekci.marketplace.features.home.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class LocationUseCase(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(region: Boolean = false) : Flow<Resource<List<LocationEntity>>> {
        return repository.getLocations(region)
    }
}