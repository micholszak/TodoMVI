package pl.olszak.todo.feature.addition

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import pl.olszak.todo.core.CoroutinesTestExtension
import pl.olszak.todo.feature.addition.interactor.AddTask
import pl.olszak.todo.feature.addition.model.AddTaskIntent
import pl.olszak.todo.feature.addition.model.AddViewState

class AddTaskViewModelTest {

    @JvmField
    @RegisterExtension
    val coroutinesExtension = CoroutinesTestExtension()

    private val addTask: AddTask = mock()
    private lateinit var addTaskViewModel: AddTaskViewModel

    @BeforeEach
    fun setUp() {
        addTaskViewModel = AddTaskViewModel(addTask)
    }

    @Test
    fun `Start with initial state`() =
        coroutinesExtension.runBlockingTest {
            addTaskViewModel.state.test {
                addTaskViewModel.subscribeToIntents(emptyFlow())
                assertThat(AddViewState()).isEqualTo(expectItem())
            }
        }

    @Test
    fun `Emit error event given that adding task fails`() =
        coroutinesExtension.runBlockingTest {
            givenAddTaskThrowsError()
            addTaskViewModel.state.test {
                val input = flowOf(AddTaskIntent.ProcessTask(taskTitle = ""))
                addTaskViewModel.subscribeToIntents(input)
                assertThat(AddViewState()).isEqualTo(expectItem())
                assertThat(AddViewState(isLoading = true)).isEqualTo(expectItem())
                assertThat(expectItem().errorEvent).isNotNull()
            }
        }

    @Test
    fun `Indicate task added to view`() {
        coroutinesExtension.runBlockingTest {
            addTaskViewModel.state.test {
                val input = flowOf(AddTaskIntent.ProcessTask(taskTitle = ""))
                addTaskViewModel.subscribeToIntents(input)
                assertThat(AddViewState()).isEqualTo(expectItem())
                assertThat(AddViewState(isLoading = true)).isEqualTo(expectItem())
                assertThat(AddViewState(isTaskAdded = true)).isEqualTo(expectItem())
            }
        }
    }

    private suspend fun givenAddTaskThrowsError() {
        whenever(addTask.execute(any())).doThrow(IllegalArgumentException())
    }
}
