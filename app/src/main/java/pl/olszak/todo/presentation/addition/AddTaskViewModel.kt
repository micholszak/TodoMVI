package pl.olszak.todo.presentation.addition

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import pl.olszak.todo.domain.DispatcherProvider
import pl.olszak.todo.domain.interactor.AddTask
import pl.olszak.todo.domain.model.AddTaskResult
import pl.olszak.todo.domain.model.Task
import pl.olszak.todo.presentation.addition.model.AddTaskSideEffect
import pl.olszak.todo.presentation.addition.model.AddTaskViewState
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTask: AddTask,
    dispatcherProvider: DispatcherProvider,
) : ViewModel(), ContainerHost<AddTaskViewState, AddTaskSideEffect> {

    override val container: Container<AddTaskViewState, AddTaskSideEffect> =
        container(
            initialState = AddTaskViewState.Idle,
            settings = Container.Settings(
                backgroundDispatcher = dispatcherProvider.io,
                orbitDispatcher = dispatcherProvider.default,
            )
        )

    fun addTaskWith(name: String) = intent {
        val task = Task(title = name)
        addTask(task = task).collect { result ->
            reduce {
                when (result) {
                    is AddTaskResult.Pending -> AddTaskViewState.Pending
                    is AddTaskResult.Success -> AddTaskViewState.Added
                    is AddTaskResult.Failure -> AddTaskViewState.Idle
                }
            }
            if (result is AddTaskResult.Failure) {
                postSideEffect(AddTaskSideEffect.EmptyFieldError)
            }
        }
    }
}
