package pl.olszak.todo.domain.list

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import pl.olszak.todo.cache.TaskDao
import pl.olszak.todo.cache.model.Priority
import pl.olszak.todo.cache.model.TaskEntity

class GetTodosTest {
    private val mockTaskDao: TaskDao = mock()

    private val getTodos = GetTodos(
        taskDao = mockTaskDao
    )

    @Test
    fun `Retrieve tasks from repository`() = runBlockingTest {
        givenGetAllTasksOperates()

        getTodos().test {
            val tasks = expectItem()
            assertThat(tasks).hasSize(0)
            expectComplete()
        }
    }

    @Test
    fun `Map entities to tasks`() = runBlockingTest {
        val title = "SCRUM"
        val description = "Agile framework"
        val entities = List(5) { index ->
            TaskEntity(
                id = index,
                title = title,
                description = description,
                priority = Priority.HIGH
            )
        }

        givenGetAllTasksOperates(returnedEntities = entities)
        getTodos().test {
            val tasks = expectItem()
            tasks.forEach { task ->
                assertThat(task.title).isEqualTo(title)
                assertThat(task.description).isEqualTo(description)
                assertThat(task.priority).isEqualTo(Priority.HIGH)
            }
            expectComplete()
        }
    }

    private fun givenGetAllTasksOperates(returnedEntities: List<TaskEntity> = emptyList()) {
        whenever(mockTaskDao.getAllTasks()).doReturn(flowOf(returnedEntities))
    }
}
