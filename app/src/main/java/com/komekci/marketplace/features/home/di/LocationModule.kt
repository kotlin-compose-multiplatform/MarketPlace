package com.komekci.marketplace.features.home.di

import com.komekci.marketplace.features.home.data.api.HomeApi
import com.komekci.marketplace.features.home.data.repository.LocationRepositoryImpl
import com.komekci.marketplace.features.home.domain.repository.LocationRepository
import com.komekci.marketplace.features.home.domain.usecase.LocationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocationModule {
    @Singleton
    @Provides
    fun provideUseCase(repository: LocationRepository): LocationUseCase = LocationUseCase(repository)

    @Singleton
    @Provides
    fun provideRepo(api: HomeApi): LocationRepository = LocationRepositoryImpl(api)
}