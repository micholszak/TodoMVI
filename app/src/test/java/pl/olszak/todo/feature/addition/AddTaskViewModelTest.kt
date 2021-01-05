package pl.olszak.todo.feature.addition

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.olszak.todo.core.test
import pl.olszak.todo.feature.addition.interactor.AddTask
import pl.olszak.todo.feature.addition.model.AddViewState

@ExperimentalCoroutinesApi
@FlowPreview
class AddTaskViewModelTest {

    private val mockAddTask: AddTask = mock()
    private lateinit var addTaskViewModel: AddTaskViewModel

    @BeforeEach
    fun setUp() {
        addTaskViewModel = AddTaskViewModel(mockAddTask)
    }

    @Test
    fun `Start with initial state`() = runBlockingTest {
        val output = addTaskViewModel.subscribeToIntent(emptyFlow())
        output.test(this)
            .assertValues { values ->
                assertThat(values).hasSize(1)
                assertThat(values.first()).isEqualTo(AddViewState())
            }.finish()
    }
}
