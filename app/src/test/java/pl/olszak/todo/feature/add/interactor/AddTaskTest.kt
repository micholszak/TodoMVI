package pl.olszak.todo.feature.add.interactor

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import pl.olszak.todo.core.TestSchedulersProvider
import pl.olszak.todo.domain.database.TaskDao
import pl.olszak.todo.domain.database.model.Priority
import pl.olszak.todo.domain.database.model.TaskEntity
import pl.olszak.todo.feature.model.Task

class AddTaskTest {
    private val mockTaskDao: TaskDao = mock()
    private val schedulersProvider = TestSchedulersProvider()

    private val addTask = AddTask(
        taskDao = mockTaskDao,
        schedulersProvider = schedulersProvider
    )

    @Test
    fun `Throw error on empty title`() {
        val task = Task()
        val testObserver = addTask.execute(task).test()
        testObserver.assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun `Add task to repository`() {
        val task = Task(
            title = "something",
            description = "something else",
            priority = Priority.HIGH
        )
        val observer = addTask.execute(task).test()
        verify(mockTaskDao).insertTask(
            TaskEntity(
                priority = task.priority,
                title = task.title,
                description = task.description
            )
        )
        observer.assertComplete()
    }
}
