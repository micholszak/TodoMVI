package pl.olszak.todo.feature.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.olszak.todo.core.emissions
import pl.olszak.todo.feature.add.interactor.AddTask
import pl.olszak.todo.feature.model.Task

class AddTaskViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockAddTask: AddTask = mock()
    private lateinit var addTaskViewModel: AddTaskViewModel

    @Before
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
        givenAddTaskIsSuccessful()
        val viewIntents = PublishSubject.create<AddTaskIntent>()
        addTaskViewModel.subscribeToIntent(viewIntents)
        viewIntents.onNext(AddTaskIntent.ProcessTask(taskTitle = "something"))

        verify(mockAddTask).execute(Task(title = "something"))
    }

    @Test
    fun `View indicates loading after requesting task processing`() {
        givenAddTaskIsSuccessful()
        val states = addTaskViewModel.viewState.emissions()
        val viewIntents = PublishSubject.create<AddTaskIntent>()
        addTaskViewModel.subscribeToIntent(viewIntents)
        viewIntents.onNext(AddTaskIntent.ProcessTask(taskTitle = "something"))

        assertThat(states[1].isLoading).isTrue()
    }

    @Test
    fun `Task addition success is marked in view`() {
        givenAddTaskIsSuccessful()
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
        givenAddTaskFails()
        val states = addTaskViewModel.viewState.emissions()
        val viewIntents = PublishSubject.create<AddTaskIntent>()
        addTaskViewModel.subscribeToIntent(viewIntents)
        viewIntents.onNext(AddTaskIntent.ProcessTask(taskTitle = ""))

        val finalState = states[2]
        assertThat(finalState.isLoading).isFalse()
        assertThat(finalState.errorEvent).isNotNull()
        assertThat(finalState.errorEvent)
    }

    private fun givenAddTaskIsSuccessful() {
        whenever(mockAddTask.execute(any())).doReturn(Completable.complete())
    }

    private fun givenAddTaskFails() {
        whenever(mockAddTask.execute(any())).doReturn(Completable.error(RuntimeException()))
    }
}
