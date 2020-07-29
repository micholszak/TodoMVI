package pl.olszak.todo.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Single<List<TaskEntity>>

    @Insert
    fun insertTask(entity: TaskEntity)
}