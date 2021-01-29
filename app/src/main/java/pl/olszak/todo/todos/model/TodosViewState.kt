package pl.olszak.todo.todos.model

import pl.olszak.todo.core.ViewState

data class TodosViewState(
    val tasks: List<TaskViewItem> = emptyList()
) : ViewState
