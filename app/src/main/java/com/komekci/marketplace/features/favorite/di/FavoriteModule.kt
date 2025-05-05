package com.komekci.marketplace.features.favorite.di

import com.komekci.marketplace.core.common.ApiClient
import com.komekci.marketplace.core.common.ApiConverter
import com.komekci.marketplace.core.common.Constant
import com.komekci.marketplace.features.favorite.data.api.FavoriteApi
import com.komekci.marketplace.features.favorite.data.repository.FavoriteRepositoryImpl
import com.komekci.marketplace.features.favorite.domain.repository.FavoriteRepository
import com.komekci.marketplace.features.favorite.domain.usecase.FavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FavoriteModule {
    @Provides
    @Singleton
    fun provideUseCase(repository: FavoriteRepository): FavoriteUseCase = FavoriteUseCase(repository)

    @Provides
    @Singleton
    fun provideRepository(api: FavoriteApi): FavoriteRepository = FavoriteRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideFavApi(): FavoriteApi {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(ApiConverter.Converter)
            .client(ApiClient.client())
            .build()
            .create(FavoriteApi::class.java)
    }
}