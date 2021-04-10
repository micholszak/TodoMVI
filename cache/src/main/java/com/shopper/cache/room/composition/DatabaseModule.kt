package com.shopper.cache.room.composition

import android.app.Application
import androidx.room.Room
import com.shopper.cache.room.ShopperDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DatabaseModule {

    @Provides
    @Singleton
    fun provideShopperDatabase(application: Application): ShopperDatabase =
        Room.databaseBuilder(application, ShopperDatabase::class.java, "shopperDatabase")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideProductsDao(database: ShopperDatabase) =
        database.productsDao()
}
