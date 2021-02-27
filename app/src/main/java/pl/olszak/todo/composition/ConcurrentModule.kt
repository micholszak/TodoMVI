package pl.olszak.todo.composition

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.olszak.todo.domain.DefaultCoroutineScope
import pl.olszak.todo.domain.DefaultDispatcherProvider
import pl.olszak.todo.domain.DispatcherProvider
import pl.olszak.todo.domain.IOCoroutineScope
import pl.olszak.todo.domain.MainCoroutineScope
import pl.olszak.todo.domain.MainImmediateCoroutineScope
import pl.olszak.todo.domain.defaultCoroutineScope
import pl.olszak.todo.domain.ioCoroutineScope
import pl.olszak.todo.domain.mainCoroutineScope
import pl.olszak.todo.domain.mainImmediateCoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ConcurrentModule {

    @Provides
    @Singleton
    fun provideMainCoroutineScope(): MainCoroutineScope =
        mainCoroutineScope()

    @Provides
    @Singleton
    fun provideIOCoroutineScope(): IOCoroutineScope =
        ioCoroutineScope()

    @Provides
    @Singleton
    fun provideDefaultCoroutineScope(): DefaultCoroutineScope =
        defaultCoroutineScope()

    @Provides
    @Singleton
    fun provideMainImmediateCoroutineScope(): MainImmediateCoroutineScope =
        mainImmediateCoroutineScope()

    @Provides
    @Singleton
    fun provideDispatcherProvider(provider: DefaultDispatcherProvider): DispatcherProvider =
        provider
}
