package pl.olszak.todo.feature.todos.model

import pl.olszak.todo.core.ViewState

data class TodosViewState(
    val tasks: List<TaskViewItem> = emptyList()
) : ViewState
