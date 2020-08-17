package pl.olszak.todo.feature.add.interactor

import io.reactivex.Completable
import pl.olszak.todo.core.SchedulersProvider
import pl.olszak.todo.domain.database.TaskDao
import pl.olszak.todo.domain.database.model.TaskEntity
import pl.olszak.todo.feature.data.Task
import javax.inject.Inject

class AddTask @Inject constructor(
    private val taskDao: TaskDao,
    private val schedulersProvider: SchedulersProvider
) {

    fun execute(task: Task): Completable {
        if (task.title.isEmpty()) {
            return Completable.error(IllegalArgumentException("Task missing title"))
        }
        return attemptToAddTask(task)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main())
    }

    private fun attemptToAddTask(task: Task): Completable =
        Completable.create { emitter ->
            val entity = TaskEntity(
                priority = task.priority,
                description = task.description,
                title = task.title
            )
            taskDao.insertTask(entity)
            emitter.onComplete()
        }
}
