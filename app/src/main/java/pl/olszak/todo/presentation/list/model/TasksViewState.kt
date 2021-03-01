package pl.olszak.todo.presentation.list.model

import pl.olszak.todo.view.list.model.TaskViewItem

data class TasksViewState(
    val tasks: List<TaskViewItem> = emptyList()
)
