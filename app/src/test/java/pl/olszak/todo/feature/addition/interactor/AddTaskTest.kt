package pl.olszak.todo.feature.addition.interactor

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pl.olszak.todo.core.concurrent.compositeTestCoroutineScope
import pl.olszak.todo.domain.database.TaskDao
import pl.olszak.todo.domain.database.model.Priority
import pl.olszak.todo.domain.database.model.TaskEntity
import pl.olszak.todo.feature.data.Task

class AddTaskTest {
    private val mockTaskDao: TaskDao = mock()
    private val scope = compositeTestCoroutineScope()

    private val addTask = AddTask(
        taskDao = mockTaskDao,
        scope = scope
    )

    @Test
    fun `Throw error on empty title`() {
        assertThrows<IllegalArgumentException> {
            runBlocking {
                val task = Task()
                addTask.execute(task)
            }
        }
    }

    @Test
    fun `Add task to repository`() = runBlocking {
        val task = Task(
            title = "something",
            description = "something else",
            priority = Priority.HIGH
        )
        addTask.execute(task)
        verify(mockTaskDao).insertTask(
            TaskEntity(
                priority = task.priority,
                title = task.title,
                description = task.description
            )
        )
    }
}
