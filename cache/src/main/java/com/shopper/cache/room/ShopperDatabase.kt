package com.shopper.cache.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shopper.cache.room.model.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
internal abstract class ShopperDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "shopperDatabase"

        fun getInstance(context: Context): ShopperDatabase =
            Room.databaseBuilder(context, ShopperDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun productsDao(): ProductsDao
}
