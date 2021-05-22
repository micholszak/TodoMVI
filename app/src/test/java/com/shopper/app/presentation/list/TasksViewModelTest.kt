package com.shopper.app.presentation.list

import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import com.shopper.app.domain.InstantTaskExecutorExtension
import com.shopper.app.domain.TestDispatcherProvider
import com.shopper.app.domain.interactor.GetTasks
import com.shopper.app.domain.model.Task
import com.shopper.app.presentation.list.model.TasksViewState
import com.shopper.app.view.list.model.TaskViewItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.orbitmvi.orbit.assert
import org.orbitmvi.orbit.test

@ExtendWith(InstantTaskExecutorExtension::class)
class TasksViewModelTest {

    companion object {
        private const val DEFAULT_SIZE = 5
    }

    private val mockGetTasks: GetTasks = mock()
    private val dispatcherProvider = TestDispatcherProvider()

    @Test
    fun `Start requesting for database updates during initialisation`() {
        givenThatGetTasksReturnsWith(flowOf(emptyList()))
        val initialState = TasksViewState()
        val viewModel = TasksViewModel(mockGetTasks, dispatcherProvider).test(
            initialState = initialState,
            runOnCreate = true
        )

        viewModel.assert(initialState)
        verify(mockGetTasks).invoke()
    }

    @Test
    fun `Update the list with new values`() {
        val firstTasks = createTasks()
        givenThatGetTasksReturnsWith(flowOf(firstTasks))

        val initialState = TasksViewState()
        val viewModel = TasksViewModel(mockGetTasks, dispatcherProvider).test(
            initialState = initialState,
            runOnCreate = true
        )
        viewModel.assert(initialState) {
            states(
                { TasksViewState(tasks = createTaskViewItems()) }
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

        val initialState = TasksViewState()
        val viewModel = TasksViewModel(mockGetTasks, dispatcherProvider).test(
            initialState = initialState,
            runOnCreate = true
        )
        viewModel.assert(initialState) {
            states(
                { TasksViewState(tasks = createTaskViewItems(5)) },
                { TasksViewState(tasks = createTaskViewItems(6)) },
                { TasksViewState(tasks = createTaskViewItems(7)) }
            )
        }
    }

    private fun givenThatGetTasksReturnsWith(tasksFlow: Flow<List<Task>>) {
        whenever(mockGetTasks.invoke()).doReturn(tasksFlow)
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
