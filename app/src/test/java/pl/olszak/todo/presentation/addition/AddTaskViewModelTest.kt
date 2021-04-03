package pl.olszak.todo.presentation.addition

import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.orbitmvi.orbit.assert
import org.orbitmvi.orbit.test
import pl.olszak.todo.cache.TaskDao
import pl.olszak.todo.domain.InstantTaskExecutorExtension
import pl.olszak.todo.domain.TestDispatcherProvider
import pl.olszak.todo.domain.interactor.AddTask
import pl.olszak.todo.presentation.addition.model.AddTaskSideEffect
import pl.olszak.todo.presentation.addition.model.AddTaskViewState

@ExtendWith(InstantTaskExecutorExtension::class)
class AddTaskViewModelTest {

    private val dispatcherProvider = TestDispatcherProvider()
    private val mockTaskDao: TaskDao = mock()
    private val addTask = AddTask(
        taskDao = mockTaskDao,
        dispatcherProvider = dispatcherProvider
    )

    @Test
    fun `Start with initial state`() {
        val initialState = AddTaskViewState.Idle
        val viewModel = AddTaskViewModel(addTask, dispatcherProvider)
            .test(AddTaskViewState.Idle)

        viewModel.assert(initialState)
    }

    @Test
    fun `Emit error event given that adding task fails`() = runBlockingTest {
        val initialState = AddTaskViewState.Idle
        val viewModel = AddTaskViewModel(addTask, dispatcherProvider)
            .test(initialState)
        viewModel.addTaskWith(name = "")
        viewModel.assert(initialState) {
            states(
                { AddTaskViewState.Pending },
                { AddTaskViewState.Idle }
            )
            postedSideEffects(
                AddTaskSideEffect.EmptyFieldError,
            )
        }
    }

    @Test
    fun `Indicate task added to view`() {
        val initialState = AddTaskViewState.Idle
        val viewModel = AddTaskViewModel(addTask, dispatcherProvider)
            .test(initialState)
        viewModel.addTaskWith("some name")
        viewModel.assert(initialState) {
            states(
                { AddTaskViewState.Pending },
                { AddTaskViewState.Added }
            )
        }
    }
}
