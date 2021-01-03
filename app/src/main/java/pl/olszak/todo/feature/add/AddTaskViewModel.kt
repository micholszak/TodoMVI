package pl.olszak.todo.feature.add

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.plus
import pl.olszak.todo.core.Reducer
import pl.olszak.todo.core.ViewStateEvent
import pl.olszak.todo.feature.add.interactor.AddTask
import pl.olszak.todo.feature.data.Task
import timber.log.Timber

class AddTaskViewModel @ViewModelInject constructor(private val addTask: AddTask) : ViewModel() {

    private val mutableState: MutableStateFlow<AddViewState> = MutableStateFlow(AddViewState())
    val viewState: StateFlow<AddViewState> = mutableState

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

    @OptIn(ExperimentalCoroutinesApi::class)
    fun subscribeToIntent(taskIntent: Flow<AddTaskIntent>) {
        taskIntent.flatMapLatest { intent ->
            when (intent) {
                is AddTaskIntent.ProcessTask -> {
                    val task = Task(title = intent.taskTitle)
                    flow {
                        emit(AddTaskResult.Pending)
                        try {
                            addTask.execute(task)
                            emit(AddTaskResult.Added)
                        } catch (e: Exception) {
                            emit(AddTaskResult.Failure)
                        }
                    }
                }
            }
        }.scan(AddViewState(), reducer)
            .onEach { newState ->
                mutableState.value = newState
            }
            .shareIn(scope = viewModelScope + CoroutineExceptionHandler { _, throwable ->
                Timber.e(throwable)
            }, started = SharingStarted.Eagerly)
    }
}
