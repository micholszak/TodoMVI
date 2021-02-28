package pl.olszak.todo.domain.interactor

import kotlinx.coroutines.withContext
import pl.olszak.todo.cache.TaskDao
import pl.olszak.todo.cache.model.TaskEntity
import pl.olszak.todo.domain.DispatcherProvider
import pl.olszak.todo.domain.model.Task
import javax.inject.Inject

class AddTask @Inject constructor(
    private val taskDao: TaskDao,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend operator fun invoke(task: Task) =
        withContext(dispatcherProvider.io) {
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
