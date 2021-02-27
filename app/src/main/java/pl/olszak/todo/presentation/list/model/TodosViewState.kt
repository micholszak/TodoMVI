package pl.olszak.todo.presentation.list.model

import pl.olszak.todo.domain.ViewState
import pl.olszak.todo.view.list.model.TaskViewItem

data class TodosViewState(
    val sorted: Boolean = false,
    val tasks: List<TaskViewItem> = emptyList()
) : ViewState
