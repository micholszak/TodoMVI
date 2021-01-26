package pl.olszak.todo.feature.todos.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import pl.olszak.todo.core.concurrent.IOCoroutineScope
import pl.olszak.todo.domain.database.TaskDao
import pl.olszak.todo.domain.database.model.TaskEntity
import javax.inject.Inject

class GetTodos @Inject constructor(
    private val taskDao: TaskDao,
    private val scope: IOCoroutineScope
) {

    suspend operator fun invoke(): Flow<List<TaskEntity>> =
        withContext(context = scope.coroutineContext) {
            taskDao.getAllTasks()
        }
}
