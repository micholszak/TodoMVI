package pl.olszak.todo.domain.addition

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pl.olszak.todo.cache.TaskDao
import pl.olszak.todo.cache.model.Priority
import pl.olszak.todo.cache.model.TaskEntity
import pl.olszak.todo.core.domain.compositeTestCoroutineScope
import pl.olszak.todo.domain.model.Task

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
                addTask(task)
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
        addTask(task)
        verify(mockTaskDao).insertTask(
            TaskEntity(
                priority = task.priority,
                title = task.title,
                description = task.description
            )
        )
    }

    @Test
    fun `Throw error upstream when adding to repository throws error`() {
        assertThrows<Exception> {
            runBlocking {
                whenever(mockTaskDao.insertTask(any())) doThrow Exception()
                val task = Task(
                    title = "something"
                )

                addTask(task)
            }
        }
    }
}
