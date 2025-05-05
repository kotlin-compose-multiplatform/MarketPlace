package com.komekci.marketplace.features.home.di

import com.komekci.marketplace.core.common.ApiClient
import com.komekci.marketplace.core.common.ApiConverter
import com.komekci.marketplace.core.common.Constant
import com.komekci.marketplace.features.home.data.api.HomeApi
import com.komekci.marketplace.features.home.data.repository.ShopRepositoryImpl
import com.komekci.marketplace.features.home.domain.repository.ShopsRepository
import com.komekci.marketplace.features.home.domain.usecase.ShopsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ShopsModule {

    @Singleton
    @Provides
    fun provideShopsUseCase(repo: ShopsRepository): ShopsUseCase {
        return ShopsUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideShopsRepo(api: HomeApi): ShopsRepository = ShopRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideHomeApi(): HomeApi = Retrofit.Builder()
        .addConverterFactory(ApiConverter.Converter)
        .client(ApiClient.client())
        .baseUrl(Constant.BASE_URL)
        .build()
        .create(HomeApi::class.java)

}