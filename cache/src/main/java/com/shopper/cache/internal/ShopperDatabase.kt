package com.shopper.cache.internal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shopper.cache.internal.model.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
internal abstract class ShopperDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "shopperDatabase"

        private var INSTANCE: ShopperDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ShopperDatabase {
            val instance: ShopperDatabase? = INSTANCE
            return if (instance != null) {
                instance
            } else {
                val newInstance = newInstance(context)
                INSTANCE = newInstance
                newInstance
            }
        }

        private fun newInstance(context: Context): ShopperDatabase =
            Room.databaseBuilder(context, ShopperDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun productsDao(): ProductDao
}
