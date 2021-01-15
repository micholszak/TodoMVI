package pl.olszak.todo.feature.addition.interactor

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import pl.olszak.todo.core.concurrent.IOCoroutineScope
import pl.olszak.todo.domain.database.TaskDao
import pl.olszak.todo.domain.database.model.TaskEntity
import pl.olszak.todo.feature.data.Task
import javax.inject.Inject

class AddTask @Inject constructor(
    private val taskDao: TaskDao,
    private val scope: IOCoroutineScope
) {

    suspend operator fun invoke(task: Task): Job =
        coroutineScope {
            if (task.title.isEmpty()) {
                throw IllegalArgumentException("Task missing title")
            }
            scope.launch(context = coroutineContext.job) {
                attemptToAddTask(task)
            }
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
