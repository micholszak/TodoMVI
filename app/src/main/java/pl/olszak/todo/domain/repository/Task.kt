package pl.olszak.todo.domain.repository

data class Task(
    val title: String,
    val description: String,
    val priority: Priority
)