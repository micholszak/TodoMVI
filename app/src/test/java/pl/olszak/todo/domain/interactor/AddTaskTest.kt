package pl.olszak.todo.domain.interactor

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.olszak.todo.cache.TaskDao
import pl.olszak.todo.cache.model.Priority
import pl.olszak.todo.cache.model.TaskEntity
import pl.olszak.todo.domain.TestDispatcherProvider
import pl.olszak.todo.domain.model.AddTaskResult
import pl.olszak.todo.domain.model.Task

class AddTaskTest {
    private val mockTaskDao: TaskDao = mock()
    private val dispatcherProvider = TestDispatcherProvider()

    private val addTask = AddTask(
        taskDao = mockTaskDao,
        dispatcherProvider = dispatcherProvider
    )

    @Test
    fun `Start with pending state`() = runBlocking {
        val task = Task()
        addTask(task).test {
            assertThat(AddTaskResult.Pending).isEqualTo(expectItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Fail the addition on empty title with IllegalArgumentException`() = runBlocking {
        val task = Task()
        addTask(task = task).test {
            assertThat(AddTaskResult.Pending).isEqualTo(expectItem())
            val result = expectItem()
            val failure = result as AddTaskResult.Failure
            assertThat(failure.throwable).isInstanceOf(IllegalArgumentException::class.java)
            expectComplete()
        }
    }

    @Test
    fun `Succeed the addition given the task is valid`() = runBlocking {
        val task = Task(
            title = "something",
            description = "something else",
            priority = Priority.HIGH
        )
        addTask(task = task).test {
            assertThat(AddTaskResult.Pending).isEqualTo(expectItem())
            verify(mockTaskDao).insertTask(
                TaskEntity(
                    priority = task.priority,
                    title = task.title,
                    description = task.description
                )
            )
            assertThat(AddTaskResult.Success).isEqualTo(expectItem())
            expectComplete()
        }
    }

    @Test
    fun `Fail the addition when service fails to process the request`() = runBlockingTest {
        whenever(mockTaskDao.insertTask(any())).doThrow(IllegalStateException())

        val task = Task(title = "not an empty title")
        addTask(task = task).test {
            assertThat(AddTaskResult.Pending).isEqualTo(expectItem())
            val failure = expectItem() as AddTaskResult.Failure
            assertThat(failure.throwable).isInstanceOf(IllegalStateException::class.java)
            expectComplete()
        }
    }
}
