package com.komekci.marketplace.features.home.di

import com.komekci.marketplace.features.home.data.api.HomeApi
import com.komekci.marketplace.features.home.data.repository.CategoryRepositoryImpl
import com.komekci.marketplace.features.home.domain.repository.CategoryRepository
import com.komekci.marketplace.features.home.domain.usecase.CategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CategoryModule {
    @Singleton
    @Provides
    fun provideUseCase(repository: CategoryRepository): CategoryUseCase =
        CategoryUseCase(repository)

    @Singleton
    @Provides
    fun provideRepository(api: HomeApi): CategoryRepository = CategoryRepositoryImpl(api)
}