package pl.olszak.todo.domain.model

import pl.olszak.todo.cache.model.Priority

data class Task(
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.LOW
)
