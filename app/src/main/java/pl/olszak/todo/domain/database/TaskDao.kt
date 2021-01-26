package pl.olszak.todo.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import pl.olszak.todo.domain.database.model.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Insert
    suspend fun insertTask(entity: TaskEntity)

    fun getAllTasksDistinct(): Flow<List<TaskEntity>> =
        getAllTasks().distinctUntilChanged()
}
