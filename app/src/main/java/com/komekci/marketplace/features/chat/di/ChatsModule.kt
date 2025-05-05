package com.komekci.marketplace.features.chat.di

import com.komekci.marketplace.core.common.ApiClient
import com.komekci.marketplace.core.common.ApiConverter
import com.komekci.marketplace.core.common.Constant
import com.komekci.marketplace.features.chat.data.api.ChatApi
import com.komekci.marketplace.features.chat.data.repository.ChatsRepositoryImpl
import com.komekci.marketplace.features.chat.domain.repository.ChatsRepository
import com.komekci.marketplace.features.chat.domain.usecase.ChatsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ChatsModule {
    @Singleton
    @Provides
    fun provideChatsUseCase(repository: ChatsRepository): ChatsUseCase = ChatsUseCase(repository)

    @Singleton
    @Provides
    fun provideChatsRepository(api: ChatApi): ChatsRepository = ChatsRepositoryImpl(api)

    @Singleton
    @Provides
    fun chatApi(): ChatApi {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(ApiConverter.Converter)
            .client(ApiClient.client())
            .build()
            .create(ChatApi::class.java)
    }




}