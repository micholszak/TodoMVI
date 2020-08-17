package pl.olszak.todo.feature.add

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import pl.olszak.todo.feature.add.interactor.AddTask
import pl.olszak.todo.feature.model.Task
import javax.inject.Inject

class AddTaskProcessor @Inject constructor(private val addTask: AddTask) {

    val intentProcessor: ObservableTransformer<AddTaskIntent, AddTaskResult> =
        ObservableTransformer { observableIntent ->
            observableIntent.switchMap { intent ->
                when (intent) {
                    is AddTaskIntent.ProcessTask -> addTaskWith(intent.taskTitle)
                }
            }
        }

    private fun addTaskWith(title: String): Observable<AddTaskResult> =
        addTask.execute(Task(title = title))
            .andThen(Observable.just<AddTaskResult>(AddTaskResult.Added))
            .startWith(AddTaskResult.Pending)
            .onErrorReturn { AddTaskResult.Failure }
}
