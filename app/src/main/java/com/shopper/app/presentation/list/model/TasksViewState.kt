package com.shopper.app.presentation.list.model

import com.shopper.app.view.list.model.TaskViewItem

data class TasksViewState(
    val tasks: List<TaskViewItem> = emptyList()
)
