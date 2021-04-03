package pl.olszak.todo.domain.model

sealed class AddTaskResult {
    object Pending : AddTaskResult()
    object Success : AddTaskResult()
    data class Failure(val throwable: Throwable) : AddTaskResult()
}
