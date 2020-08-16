package pl.olszak.todo.composition

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pl.olszak.todo.core.AndroidSchedulersProvider
import pl.olszak.todo.core.SchedulersProvider

@Module
@InstallIn(ApplicationComponent::class)
abstract class CoreModule {

    @Binds
    abstract fun bindSchedulerProvider(androidSchedulersProvider: AndroidSchedulersProvider): SchedulersProvider
}
