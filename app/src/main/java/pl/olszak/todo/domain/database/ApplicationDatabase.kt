package pl.olszak.todo.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.olszak.todo.domain.database.model.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
@TypeConverters(DatabaseConverters::class)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}
