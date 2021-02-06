package pl.olszak.todo.presentation.list.model

import pl.olszak.todo.core.ViewState
import pl.olszak.todo.view.list.model.TaskViewItem

data class TodosViewState(
    val filtered: Boolean = false,
    val tasks: List<TaskViewItem> = emptyList()
) : ViewState
