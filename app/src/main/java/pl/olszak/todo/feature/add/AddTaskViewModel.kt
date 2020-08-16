package pl.olszak.todo.feature.add

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import pl.olszak.todo.core.Reducer
import pl.olszak.todo.core.ViewStateEvent

class AddTaskViewModel @ViewModelInject constructor(
    private val processor: AddTaskProcessor
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val mutableViewState = MutableLiveData<AddViewState>()
    val viewState: LiveData<AddViewState> = mutableViewState

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

    fun subscribeToIntent(taskIntent: Observable<AddTaskIntent>) {
        compositeDisposable +=
            taskIntent.compose(processor.intentProcessor)
                .scan<AddViewState>(AddViewState(), reducer)
                .subscribeBy(
                    onNext = { state ->
                        mutableViewState.postValue(state)
                    }
                )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }
}
