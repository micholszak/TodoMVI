package pl.olszak.todo.feature.todos

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.olszak.todo.feature.data.Task
import pl.olszak.todo.feature.todos.interactor.GetTodos
import pl.olszak.todo.feature.todos.model.TaskViewItem
import pl.olszak.todo.feature.todos.model.TodosViewState

class TodosViewModel @ViewModelInject constructor(
    private val getTodos: GetTodos
) : ViewModel() {

    private val mutableState: MutableStateFlow<TodosViewState> = MutableStateFlow(TodosViewState())
    val state: StateFlow<TodosViewState> = mutableState

    init {
        viewModelScope.launch {
            getTodos().collect(::processTasks)
        }
    }

    private fun processTasks(tasks: List<Task>) {
        val taskViewItems = tasks.map { task ->
            TaskViewItem(
                title = task.title,
                description = task.description
            )
        }
        mutableState.value = TodosViewState(taskViewItems)
    }
}
