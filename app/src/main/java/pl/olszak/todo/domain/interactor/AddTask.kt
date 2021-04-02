package pl.olszak.todo.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import pl.olszak.todo.cache.TaskDao
import pl.olszak.todo.cache.model.TaskEntity
import pl.olszak.todo.domain.DispatcherProvider
import pl.olszak.todo.domain.model.AddTaskResult
import pl.olszak.todo.domain.model.Task
import javax.inject.Inject

class AddTask @Inject constructor(
    private val taskDao: TaskDao,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend operator fun invoke(task: Task): Flow<AddTaskResult> = flow {
        if (task.title.isEmpty()) {
            emit(
                AddTaskResult.Failure(
                    IllegalArgumentException("Task missing title")
                )
            )
            return@flow
        }
        try {
            attemptToAddTask(task)
            emit(AddTaskResult.Success)
        } catch (e: Exception) {
            emit(AddTaskResult.Failure(e))
        }
    }.onStart {
        emit(AddTaskResult.Pending)
    }.flowOn(dispatcherProvider.io)

    private suspend fun attemptToAddTask(task: Task) {
        val entity = TaskEntity(
            priority = task.priority,
            description = task.description,
            title = task.title
        )
        taskDao.insertTask(entity)
    }
}
