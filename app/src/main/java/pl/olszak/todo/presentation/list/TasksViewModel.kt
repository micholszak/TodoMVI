package pl.olszak.todo.presentation.list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import pl.olszak.todo.domain.DispatcherProvider
import pl.olszak.todo.domain.interactor.GetTasks
import pl.olszak.todo.domain.model.Task
import pl.olszak.todo.presentation.list.model.TodosViewState
import pl.olszak.todo.view.list.model.TaskViewItem
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasks: GetTasks,
    dispatcherProvider: DispatcherProvider
) : ViewModel(), ContainerHost<TodosViewState, Unit> {

    override val container: Container<TodosViewState, Unit> =
        container(
            initialState = TodosViewState(),
            settings = Container.Settings(
                backgroundDispatcher = dispatcherProvider.io,
                orbitDispatcher = dispatcherProvider.default
            )
        ) {
            subscribeToDatabaseUpdates()
        }

    private fun subscribeToDatabaseUpdates() = intent {
        getTasks().map(::mapTasks).collect { items ->
            reduce {
                state.copy(
                    tasks = items
                )
            }
        }
    }

    private fun mapTasks(tasks: List<Task>): List<TaskViewItem> {
        return tasks.map { task ->
            TaskViewItem(
                title = task.title,
                description = task.description
            )
        }
    }
}
