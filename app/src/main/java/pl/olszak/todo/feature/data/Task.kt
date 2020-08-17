package pl.olszak.todo.feature.data

import pl.olszak.todo.domain.database.model.Priority

data class Task(
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.LOW
)
