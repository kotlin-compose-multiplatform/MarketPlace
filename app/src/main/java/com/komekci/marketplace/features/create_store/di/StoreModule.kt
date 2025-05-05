package com.komekci.marketplace.features.create_store.di

import com.komekci.marketplace.core.common.ApiClient
import com.komekci.marketplace.core.common.ApiConverter
import com.komekci.marketplace.core.common.Constant
import com.komekci.marketplace.features.create_store.data.api.StoreApi
import com.komekci.marketplace.features.create_store.data.repository.StoreRepositoryImpl
import com.komekci.marketplace.features.create_store.domain.repository.StoreRepository
import com.komekci.marketplace.features.create_store.domain.usecase.StoreUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StoreModule {
    @Provides
    @Singleton
    fun providesUseCase(repository: StoreRepository): StoreUseCase = StoreUseCase(repository)

    @Provides
    @Singleton
    fun providesRepository(api: StoreApi): StoreRepository = StoreRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideApi(): StoreApi {
        return Retrofit.Builder()
            .addConverterFactory(ApiConverter.Converter)
            .baseUrl(Constant.BASE_URL)
            .client(ApiClient.client())
            .build()
            .create(StoreApi::class.java)
    }
}