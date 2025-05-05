package com.komekci.marketplace.features.basket.di

import com.komekci.marketplace.core.common.ApiClient
import com.komekci.marketplace.core.common.ApiConverter
import com.komekci.marketplace.core.common.Constant
import com.komekci.marketplace.features.basket.data.api.BasketApi
import com.komekci.marketplace.features.basket.data.repository.BasketRepositoryImpl
import com.komekci.marketplace.features.basket.domain.repository.BasketRepository
import com.komekci.marketplace.features.basket.domain.usecase.BasketUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BasketModule {
    @Provides
    @Singleton
    fun providesUseCase(repository: BasketRepository): BasketUseCase {
        return BasketUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideRepository(api: BasketApi) : BasketRepository {
        return BasketRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideBasketApi() : BasketApi {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(ApiConverter.Converter)
            .client(ApiClient.client())
            .build()
            .create(BasketApi::class.java)
    }
}