package com.shopper.cache.room.composition

import android.app.Application
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
        ShopperDatabase.getInstance(application)

    @Provides
    @Singleton
    fun provideProductsDao(database: ShopperDatabase) =
        database.productsDao()
}
