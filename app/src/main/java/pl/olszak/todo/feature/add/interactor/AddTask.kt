package pl.olszak.todo.feature.add.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import pl.olszak.todo.domain.database.TaskDao
import pl.olszak.todo.domain.database.model.TaskEntity
import pl.olszak.todo.feature.data.Task
import javax.inject.Inject

class AddTask @Inject constructor(private val taskDao: TaskDao) {

    suspend fun execute(task: Task) {
        withContext(SupervisorJob() + Dispatchers.IO) {
            if (task.title.isEmpty()) {
                throw IllegalArgumentException("Task missing title")
            }
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
