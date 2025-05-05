package com.komekci.marketplace.features.profile.di

import com.komekci.marketplace.core.common.ApiClient
import com.komekci.marketplace.core.common.ApiConverter
import com.komekci.marketplace.core.common.Constant
import com.komekci.marketplace.features.profile.data.api.ProfileApi
import com.komekci.marketplace.features.profile.data.repository.ProfileRepositoryImpl
import com.komekci.marketplace.features.profile.domain.repository.ProfileRepository
import com.komekci.marketplace.features.profile.domain.usecase.ProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProfileModule {
    @Provides
    @Singleton
    fun provideProfileUseCase(repository: ProfileRepository): ProfileUseCase = ProfileUseCase(repository)

    @Singleton
    @Provides
    fun provideProfileRepository(api: ProfileApi): ProfileRepository = ProfileRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideProfileApi(): ProfileApi {
        return Retrofit.Builder()
            .addConverterFactory(ApiConverter.Converter)
            .baseUrl(Constant.BASE_URL)
            .client(ApiClient.client())
            .build()
            .create(ProfileApi::class.java)
    }
}