package com.komekci.marketplace.features.home.di

import com.komekci.marketplace.features.home.data.api.HomeApi
import com.komekci.marketplace.features.home.data.repository.DiscountRepositoryImpl
import com.komekci.marketplace.features.home.domain.repository.DiscountRepository
import com.komekci.marketplace.features.home.domain.usecase.DiscountUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiscountModule {
    @Singleton
    @Provides
    fun provideUseCase(repository: DiscountRepository): DiscountUseCase =
        DiscountUseCase(repository)

    @Singleton
    @Provides
    fun provideRepository(api: HomeApi): DiscountRepository = DiscountRepositoryImpl(api)
}