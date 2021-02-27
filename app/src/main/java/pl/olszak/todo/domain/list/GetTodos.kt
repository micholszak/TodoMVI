package pl.olszak.todo.domain.list

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.olszak.todo.cache.TaskDao
import pl.olszak.todo.cache.model.TaskEntity
import pl.olszak.todo.domain.model.Task
import javax.inject.Inject

open class GetTodos @Inject constructor(
    private val taskDao: TaskDao,
) {

    fun execute(): Flow<List<Task>> =
        taskDao.getAllTasks()
            .map(::mapEntities)

    private fun mapEntities(entities: List<TaskEntity>): List<Task> =
        entities.map { entity ->
            Task(
                title = entity.title,
                description = entity.description,
                priority = entity.priority
            )
        }
}
