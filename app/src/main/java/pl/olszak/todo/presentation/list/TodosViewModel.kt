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
class TodosViewModel @Inject constructor(
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

    fun sortData() = intent {
        reduce {
            state.copy(
                sorted = true,
                tasks = state.tasks.sortedBy(TaskViewItem::title)
            )
        }
    }

    private fun subscribeToDatabaseUpdates() = intent {
        getTasks.execute().map(::mapTasks).collect { items ->
            reduce {
                val newItems = if (state.sorted)
                    items.sortedBy(TaskViewItem::title)
                else
                    items

                state.copy(
                    tasks = newItems
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
