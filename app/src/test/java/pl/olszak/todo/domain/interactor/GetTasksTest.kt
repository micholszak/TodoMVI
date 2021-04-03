package pl.olszak.todo.domain.interactor

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import pl.olszak.todo.cache.TaskDao
import pl.olszak.todo.cache.model.Priority
import pl.olszak.todo.cache.model.TaskEntity

class GetTasksTest {
    private val mockTaskDao: TaskDao = mock()

    private val getTasks = GetTasks(
        taskDao = mockTaskDao
    )

    @Test
    fun `Retrieve tasks from repository`() = runBlockingTest {
        givenGetAllTasksOperates()

        getTasks().test {
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
        getTasks().test {
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
