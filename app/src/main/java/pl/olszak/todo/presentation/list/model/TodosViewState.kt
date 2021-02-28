package pl.olszak.todo.presentation.list.model

import pl.olszak.todo.view.list.model.TaskViewItem

data class TodosViewState(
    val tasks: List<TaskViewItem> = emptyList()
)
