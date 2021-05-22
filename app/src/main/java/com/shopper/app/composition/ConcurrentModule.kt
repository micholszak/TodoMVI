package com.shopper.app.composition

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.shopper.app.domain.DefaultDispatcherProvider
import com.shopper.app.domain.DispatcherProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ConcurrentModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(provider: DefaultDispatcherProvider): DispatcherProvider =
        provider
}
