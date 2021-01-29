package pl.olszak.todo.addition.interactor

import kotlinx.coroutines.withContext
import pl.olszak.todo.core.concurrent.IOCoroutineScope
import pl.olszak.todo.domain.database.TaskDao
import pl.olszak.todo.domain.database.model.TaskEntity
import pl.olszak.todo.domain.model.Task
import javax.inject.Inject

class AddTask @Inject constructor(
    private val taskDao: TaskDao,
    private val scope: IOCoroutineScope
) {

    suspend operator fun invoke(task: Task) =
        withContext(context = scope.coroutineContext) {
            if (task.title.isEmpty()) {
                throw IllegalArgumentException("Task missing title")
            }
            attemptToAddTask(task)
        }

    private suspend fun attemptToAddTask(task: Task) {
        val entity = TaskEntity(
            priority = task.priority,
            description = task.description,
            title = task.title
        )
        taskDao.insertTask(entity)
    }
}
