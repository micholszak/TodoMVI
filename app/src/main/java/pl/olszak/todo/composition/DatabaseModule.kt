package pl.olszak.todo.composition

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.olszak.todo.domain.database.ApplicationDatabase

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDatabase(application: Application): ApplicationDatabase =
        Room.databaseBuilder(application, ApplicationDatabase::class.java, "application_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideTodoDao(database: ApplicationDatabase) =
        database.taskDao()
}
