package com.komekci.marketplace.features.auth.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.komekci.marketplace.core.common.ApiClient
import com.komekci.marketplace.core.common.ApiConverter
import com.komekci.marketplace.core.common.Constant
import com.komekci.marketplace.features.auth.data.api.AuthApi
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.auth.data.repository.AuthRepositoryImpl
import com.komekci.marketplace.features.auth.domain.repository.AuthRepository
import com.komekci.marketplace.features.auth.domain.usecase.AuthUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import javax.inject.Singleton

private const val USER_PREFERENCES = "user_preferences"

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    @Singleton
    fun provideUserDataStore(@ApplicationContext context: Context): UserDataStore {
        return UserDataStore(context)
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext,USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi, userDataStore: UserDataStore): AuthRepository = AuthRepositoryImpl(api, userDataStore)

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi = Retrofit.Builder()
        .addConverterFactory(ApiConverter.Converter)
        .baseUrl(Constant.BASE_URL)
        .client(ApiClient.client())
        .build()
        .create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideAuthUseCase(authRepository: AuthRepository): AuthUseCase = AuthUseCase(authRepository)


}