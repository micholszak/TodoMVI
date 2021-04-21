package com.shopper.cache.composition

import android.content.Context
import com.shopper.cache.ProductCache
import com.shopper.cache.ProductRoomCache
import com.shopper.cache.internal.ShopperDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Provides
    @Singleton
    internal fun provideProductCache(@ApplicationContext context: Context): ProductCache {
        val database = ShopperDatabase.getInstance(context)
        return ProductRoomCache(database.productsDao())
    }
}
