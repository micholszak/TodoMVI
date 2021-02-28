package pl.olszak.todo.composition

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.olszak.todo.domain.DefaultDispatcherProvider
import pl.olszak.todo.domain.DispatcherProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ConcurrentModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(provider: DefaultDispatcherProvider): DispatcherProvider =
        provider
}
