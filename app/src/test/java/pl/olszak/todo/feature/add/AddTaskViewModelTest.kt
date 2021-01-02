package pl.olszak.todo.feature.add

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.olszak.todo.core.InstantTaskExecutionExtension
import pl.olszak.todo.feature.add.interactor.AddTask

@ExtendWith(InstantTaskExecutionExtension::class)
class AddTaskViewModelTest {

    private val mockAddTask: AddTask = mock()
    private lateinit var addTaskViewModel: AddTaskViewModel

    @BeforeEach
    fun setUp() {
        addTaskViewModel = AddTaskViewModel(processor = AddTaskProcessor(mockAddTask))
    }

    @Test
    fun `Start without state`() {
        val state = addTaskViewModel.viewState
        assertThat(state.value).isNull()
    }

    @Test
    fun `Start with default state when attached`() {
        val stateList = addTaskViewModel.viewState.emissions()
        addTaskViewModel.subscribeToIntent(Observable.empty())

        assertThat(stateList).hasSize(1)
        assertThat(stateList.first()).isEqualTo(AddViewState())
    }

    @Test
    fun `Perform task addition when requested`() {
        val viewIntents = PublishSubject.create<AddTaskIntent>()
        addTaskViewModel.subscribeToIntent(viewIntents)
        viewIntents.onNext(AddTaskIntent.ProcessTask(taskTitle = "something"))
    }

    @Test
    fun `View indicates loading after requesting task processing`() {
        val states = addTaskViewModel.viewState.emissions()
        val viewIntents = PublishSubject.create<AddTaskIntent>()
        addTaskViewModel.subscribeToIntent(viewIntents)
        viewIntents.onNext(AddTaskIntent.ProcessTask(taskTitle = "something"))

        assertThat(states[1].isLoading).isTrue()
    }

    @Test
    fun `Task addition success is marked in view`() {
        val states = addTaskViewModel.viewState.emissions()
        val viewIntents = PublishSubject.create<AddTaskIntent>()
        addTaskViewModel.subscribeToIntent(viewIntents)
        viewIntents.onNext(AddTaskIntent.ProcessTask(taskTitle = "something"))

        val finalState = states[2]
        assertThat(finalState.isLoading).isFalse()
        assertThat(finalState.isTaskAdded).isTrue()
        assertThat(states).hasSize(3)
    }

    @Test
    fun `Task addition failure returns error in view`() {
        val states = addTaskViewModel.viewState.emissions()
        val viewIntents = PublishSubject.create<AddTaskIntent>()
        addTaskViewModel.subscribeToIntent(viewIntents)
        viewIntents.onNext(AddTaskIntent.ProcessTask(taskTitle = ""))

        val finalState = states[2]
        assertThat(finalState.isLoading).isFalse()
        assertThat(finalState.errorEvent).isNotNull()
        assertThat(finalState.errorEvent)
    }
}
