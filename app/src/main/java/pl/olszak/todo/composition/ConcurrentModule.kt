package pl.olszak.todo.composition

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.olszak.todo.core.concurrent.DefaultCoroutineScope
import pl.olszak.todo.core.concurrent.IOCoroutineScope
import pl.olszak.todo.core.concurrent.MainCoroutineScope
import pl.olszak.todo.core.concurrent.MainImmediateCoroutineScope
import pl.olszak.todo.core.concurrent.defaultCoroutineScope
import pl.olszak.todo.core.concurrent.ioCoroutineScope
import pl.olszak.todo.core.concurrent.mainCoroutineScope
import pl.olszak.todo.core.concurrent.mainImmediateCoroutineScope
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
}
