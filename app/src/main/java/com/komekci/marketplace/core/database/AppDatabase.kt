package com.komekci.marketplace.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.komekci.marketplace.features.basket.data.entity.BasketLocalEntity
import com.komekci.marketplace.features.basket.data.local.BasketDao

@Database(entities = [BasketLocalEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun basketDao(): BasketDao

}