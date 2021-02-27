package pl.olszak.todo.presentation.list

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.orbitmvi.orbit.assert
import org.orbitmvi.orbit.test
import pl.olszak.todo.domain.InstantTaskExecutorExtension
import pl.olszak.todo.domain.TestDispatcherProvider
import pl.olszak.todo.domain.interactor.GetTasks
import pl.olszak.todo.domain.model.Task
import pl.olszak.todo.presentation.list.model.TodosViewState
import pl.olszak.todo.view.list.model.TaskViewItem

@ExtendWith(InstantTaskExecutorExtension::class)
class TodosViewModelTest {

    companion object {
        private const val DEFAULT_SIZE = 5
    }

    private val mockGetTasks: GetTasks = mock()
    private val dispatcherProvider = TestDispatcherProvider()

    @Test
    fun `Start requesting for database updates during initialisation`() {
        givenThatGetTasksReturnsWith(flowOf(emptyList()))
        val initialState = TodosViewState()
        val testSubject = TodosViewModel(mockGetTasks, dispatcherProvider).test(
            initialState = initialState,
            runOnCreate = true
        )

        testSubject.assert(initialState)
        verify(mockGetTasks).execute()
    }

    @Test
    fun `Update the list with new values`() {
        val firstTasks = createTasks()
        givenThatGetTasksReturnsWith(flowOf(firstTasks))

        val initialState = TodosViewState()
        val viewModel = TodosViewModel(mockGetTasks, dispatcherProvider).test(
            initialState = initialState,
            runOnCreate = true
        )
        viewModel.assert(initialState) {
            states(
                { TodosViewState(tasks = createTaskViewItems()) }
            )
        }
    }

    @Test
    fun `Update state after database update`() {
        givenThatGetTasksReturnsWith(
            flowOf(
                createTasks(5),
                createTasks(6),
                createTasks(7)
            )
        )

        val initialState = TodosViewState()
        val viewModel = TodosViewModel(mockGetTasks, dispatcherProvider).test(
            initialState = initialState,
            runOnCreate = true
        )
        viewModel.assert(initialState) {
            states(
                { TodosViewState(tasks = createTaskViewItems(5)) },
                { TodosViewState(tasks = createTaskViewItems(6)) },
                { TodosViewState(tasks = createTaskViewItems(7)) }
            )
        }
    }

    @Test
    fun `Sort data after request`() {
        val initialState = TodosViewState(
            tasks = createTaskViewItems().reversed()
        )
        val viewModel = TodosViewModel(mockGetTasks, dispatcherProvider).test(
            initialState = initialState
        )
        viewModel.sortData()
        viewModel.assert(initialState) {
            states(
                { TodosViewState(sorted = true, tasks = createTaskViewItems()) }
            )
        }
    }

    private fun givenThatGetTasksReturnsWith(tasksFlow: Flow<List<Task>>) {
        whenever(mockGetTasks.execute()).doReturn(tasksFlow)
    }

    private fun createTasks(size: Int = DEFAULT_SIZE): List<Task> =
        List(size) { index ->
            Task("$index")
        }

    private fun createTaskViewItems(size: Int = DEFAULT_SIZE): List<TaskViewItem> =
        List(size) { index ->
            TaskViewItem(title = "$index")
        }
}
