package pl.olszak.todo.composition

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.olszak.todo.cache.ApplicationDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): ApplicationDatabase =
        Room.databaseBuilder(application, ApplicationDatabase::class.java, "application_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideTodoDao(database: ApplicationDatabase) =
        database.taskDao()
}
