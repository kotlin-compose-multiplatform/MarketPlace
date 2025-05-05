package com.komekci.marketplace.features.basket.data.local

import androidx.annotation.Keep
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.komekci.marketplace.features.basket.data.entity.BasketLocalEntity

@Keep
data class BasketStore(
    val shopName: String = "",
    val shopId: Int = 0
)

@Dao
interface BasketDao {
    @Query("SELECT * FROM basketlocalentity WHERE shopId = :shopId ORDER BY id DESC")
    suspend fun getBasket(shopId: Int): List<BasketLocalEntity>?

    @Query("SELECT DISTINCT shopName, shopId FROM basketlocalentity ORDER BY id DESC")
    suspend fun getBasketStores(): List<BasketStore>?

    @Query("SELECT * FROM basketlocalentity WHERE id = :id LIMIT 1")
    suspend fun getBasketById(id: String): BasketLocalEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBasket(basket: BasketLocalEntity)

    @Query("DELETE FROM basketlocalentity WHERE id = :id")
    suspend fun deleteBasketById(id: String)

    @Query("DELETE FROM basketlocalentity WHERE shopId = :shopId")
    suspend fun deleteAll(shopId: Int)


}