package com.shopper.cache.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shopper.cache.room.model.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
internal abstract class ShopperDatabase : RoomDatabase() {

    abstract fun productsDao(): ProductsDao
}
