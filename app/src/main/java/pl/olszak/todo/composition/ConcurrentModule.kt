package pl.olszak.todo.composition

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.olszak.todo.core.domain.DefaultCoroutineScope
import pl.olszak.todo.core.domain.IOCoroutineScope
import pl.olszak.todo.core.domain.MainCoroutineScope
import pl.olszak.todo.core.domain.MainImmediateCoroutineScope
import pl.olszak.todo.core.domain.defaultCoroutineScope
import pl.olszak.todo.core.domain.ioCoroutineScope
import pl.olszak.todo.core.domain.mainCoroutineScope
import pl.olszak.todo.core.domain.mainImmediateCoroutineScope
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
