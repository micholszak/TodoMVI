package pl.olszak.todo.feature.add

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.plus
import pl.olszak.todo.core.IntentProcessor
import pl.olszak.todo.core.Reducer
import pl.olszak.todo.core.ViewStateEvent
import pl.olszak.todo.feature.add.interactor.AddTask
import pl.olszak.todo.feature.data.Task
import timber.log.Timber

class AddTaskViewModel @ViewModelInject constructor(private val addTask: AddTask) : ViewModel() {

    private val mutableState: MutableStateFlow<AddViewState> = MutableStateFlow(AddViewState())

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

    private val intentProcessor: IntentProcessor<AddTaskIntent, AddTaskResult> = { intent ->
        when (intent) {
            is AddTaskIntent.ProcessTask -> addTaskToStore(intent.taskTitle)
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun subscribeToIntent(taskIntent: Flow<AddTaskIntent>): Flow<AddViewState> {
        taskIntent.flatMapMerge(transform = intentProcessor)
            .scan(AddViewState(), reducer)
            .onEach { newState ->
                mutableState.value = newState
            }
            .shareIn(
                scope = viewModelScope + CoroutineExceptionHandler { _, throwable ->
                    Timber.e(throwable)
                },
                started = SharingStarted.Eagerly
            )
        return mutableState
    }

    private fun addTaskToStore(title: String): Flow<AddTaskResult> = flow {
        emit(AddTaskResult.Pending)
        val task = Task(title = title)
        try {
            addTask.execute(task)
            emit(AddTaskResult.Added)
        } catch (e: IllegalArgumentException) {
            emit(AddTaskResult.Failure)
        }
    }
}
