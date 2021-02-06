package pl.olszak.todo.presentation.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.coroutines.transformFlow
import org.orbitmvi.orbit.syntax.strict.orbit
import org.orbitmvi.orbit.syntax.strict.reduce
import org.orbitmvi.orbit.viewmodel.container
import pl.olszak.todo.domain.list.GetTodos
import pl.olszak.todo.domain.model.Task
import pl.olszak.todo.presentation.list.model.TodosViewState
import pl.olszak.todo.view.list.model.TaskViewItem

class TodosViewModel @ViewModelInject constructor(
    private val getTodos: GetTodos
) : ViewModel(), ContainerHost<TodosViewState, Unit> {

    override val container: Container<TodosViewState, Unit> =
        container(initialState = TodosViewState())

    init {
        subscribeToDatabaseUpdates()
    }

    fun sortData() = orbit {
        reduce {
            state.copy(
                filtered = true,
                tasks = state.tasks.sortedBy(TaskViewItem::title)
            )
        }
    }

    private fun subscribeToDatabaseUpdates() = orbit {
        transformFlow {
            getTodos().map(::mapTasks)
        }.reduce {
            val newItems = if (state.filtered)
                event.sortedBy(TaskViewItem::title)
            else
                event

            state.copy(
                tasks = newItems
            )
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
