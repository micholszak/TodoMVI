package pl.olszak.todo.presentation.addition.model

sealed class AddTaskSideEffect {
    object EmptyFieldError : AddTaskSideEffect()
}
