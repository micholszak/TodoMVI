package pl.olszak.todo.feature.todos.interactor

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import pl.olszak.todo.core.concurrent.compositeTestCoroutineScope
import pl.olszak.todo.domain.database.TaskDao
import pl.olszak.todo.domain.database.model.TaskEntity

class GetTodosTest {
    private val mockTaskDao: TaskDao = mock()
    private val scope = compositeTestCoroutineScope()

    private val getTodos = GetTodos(
        taskDao = mockTaskDao,
        scope = scope
    )

    @Test
    fun `Retrieve tasks from repository`() = runBlocking {
        whenever(mockTaskDao.getAllTasks()).doReturn(flowOf(listOf(TaskEntity())))

        getTodos().test {
            val entities = expectItem()
            assertThat(entities).hasSize(1)
            expectComplete()
        }
    }
}
