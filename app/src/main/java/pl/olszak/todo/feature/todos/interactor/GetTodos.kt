package pl.olszak.todo.feature.todos.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import pl.olszak.todo.core.concurrent.IOCoroutineScope
import pl.olszak.todo.domain.database.TaskDao
import pl.olszak.todo.domain.database.model.TaskEntity
import pl.olszak.todo.feature.data.Task
import javax.inject.Inject

class GetTodos @Inject constructor(
    private val taskDao: TaskDao,
    private val scope: IOCoroutineScope
) {

    operator fun invoke(): Flow<List<Task>> =
        taskDao.getAllTasks()
            .map(::mapEntities)
            .flowOn(scope.coroutineContext)

    private fun mapEntities(entities: List<TaskEntity>): List<Task> =
        entities.map { entity ->
            Task(
                title = entity.title,
                description = entity.description,
                priority = entity.priority
            )
        }
}
