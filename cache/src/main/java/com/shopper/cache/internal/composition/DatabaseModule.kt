package com.shopper.cache.internal.composition

import android.app.Application
import com.shopper.cache.internal.ShopperDatabase
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
        ShopperDatabase.newInstance(application)

    @Provides
    @Singleton
    fun provideProductsDao(database: ShopperDatabase) =
        database.productsDao()
}
