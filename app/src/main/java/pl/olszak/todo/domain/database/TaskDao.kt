package pl.olszak.todo.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.olszak.todo.domain.database.model.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<TaskEntity>

    @Insert
    suspend fun insertTask(entity: TaskEntity)
}
