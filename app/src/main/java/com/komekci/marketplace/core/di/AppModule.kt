package com.komekci.marketplace.core.di

import android.content.Context
import androidx.room.Room
import com.komekci.marketplace.core.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) = appDatabase(context)
}

fun appDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "komekchi_database"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration().build()
}