package com.komekci.marketplace.features.store_chat.di

import com.komekci.marketplace.features.chat.data.api.ChatApi
import com.komekci.marketplace.features.chat.data.repository.StoreChatsRepositoryImpl
import com.komekci.marketplace.features.store_chat.domain.repository.StoreChatsRepository
import com.komekci.marketplace.features.store_chat.domain.usecase.StoreChatsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StoreChatsModule {
    @Singleton
    @Provides
    fun provideChatsUseCase(repository: StoreChatsRepository): StoreChatsUseCase = StoreChatsUseCase(repository)

    @Singleton
    @Provides
    fun provideChatsRepository(api: ChatApi): StoreChatsRepository = StoreChatsRepositoryImpl(api)

}