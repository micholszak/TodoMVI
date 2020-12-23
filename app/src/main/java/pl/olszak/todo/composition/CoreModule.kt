package pl.olszak.todo.composition

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.olszak.todo.core.AndroidSchedulersProvider
import pl.olszak.todo.core.SchedulersProvider

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {

    @Binds
    abstract fun bindSchedulerProvider(androidSchedulersProvider: AndroidSchedulersProvider): SchedulersProvider
}
