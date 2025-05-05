package com.komekci.marketplace.features.product.di

import com.komekci.marketplace.core.common.ApiClient
import com.komekci.marketplace.core.common.ApiConverter
import com.komekci.marketplace.core.common.Constant
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.product.data.api.ProductApi
import com.komekci.marketplace.features.product.data.repository.CategoryRepositoryImpl
import com.komekci.marketplace.features.product.domain.repository.CategoryRepository
import com.komekci.marketplace.features.product.domain.useCase.CategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CategoryModule {

    @Provides
    @Singleton
    fun provideCategoryUseCase(categoryRepository: CategoryRepository): CategoryUseCase {
        return CategoryUseCase(categoryRepository)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(api: ProductApi, userDataStore: UserDataStore): CategoryRepository {
        return CategoryRepositoryImpl(api, userDataStore)
    }

    @Provides
    @Singleton
    fun provideProductApi(): ProductApi {
        return Retrofit.Builder()
            .addConverterFactory(ApiConverter.Converter)
            .baseUrl(Constant.BASE_URL)
            .client(ApiClient.client())
            .build()
            .create(ProductApi::class.java)
    }

}