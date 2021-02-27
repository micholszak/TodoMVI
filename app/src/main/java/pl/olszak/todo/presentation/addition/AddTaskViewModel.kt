package pl.olszak.todo.presentation.addition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import pl.olszak.todo.domain.ActionProcessor
import pl.olszak.todo.domain.Reducer
import pl.olszak.todo.domain.addition.AddTask
import pl.olszak.todo.domain.model.Task
import pl.olszak.todo.presentation.addition.model.AddTaskAction
import pl.olszak.todo.presentation.addition.model.AddTaskResult
import pl.olszak.todo.presentation.addition.model.AddViewState
import pl.olszak.todo.presentation.addition.model.FieldError
import pl.olszak.todo.view.model.ViewStateEvent
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTask: AddTask
) : ViewModel() {

    private val mutableState: MutableStateFlow<AddViewState> = MutableStateFlow(AddViewState())
    val state: StateFlow<AddViewState> = mutableState

    private val reducer: Reducer<AddViewState, AddTaskResult> = { previous, result ->
        when (result) {
            is AddTaskResult.Pending -> previous.copy(
                isLoading = true
            )
            is AddTaskResult.Added -> AddViewState(
                isLoading = false,
                isTaskAdded = true
            )
            is AddTaskResult.Failure -> previous.copy(
                isLoading = false,
                errorEvent = ViewStateEvent(FieldError.TITLE)
            )
        }
    }

    private val actionProcessor: ActionProcessor<AddTaskAction, AddTaskResult> = { intent ->
        when (intent) {
            is AddTaskAction.ProcessTask -> addTaskToStore(intent.taskTitle)
        }
    }

    fun subscribeToActions(actions: Flow<AddTaskAction>) {
        viewModelScope.launch {
            actions.flatMapMerge(transform = actionProcessor)
                .scan(AddViewState(), reducer)
                .collect { newState ->
                    mutableState.value = newState
                }
        }
    }

    private fun addTaskToStore(title: String): Flow<AddTaskResult> =
        flow {
            emit(AddTaskResult.Pending)
            val task = Task(title = title)
            addTask(task)
            emit(AddTaskResult.Added)
        }.catch {
            emit(AddTaskResult.Failure)
        }
}
