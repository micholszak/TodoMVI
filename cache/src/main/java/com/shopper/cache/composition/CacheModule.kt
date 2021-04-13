package com.shopper.cache.composition

import com.shopper.cache.ProductCache
import com.shopper.cache.ProductRoomCache
import com.shopper.cache.internal.composition.DatabaseModule
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        DatabaseModule::class
    ]
)
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    internal abstract fun bindProductCache(cache: ProductRoomCache): ProductCache
}
