package com.shopper.app.presentation.addition.model

sealed class AddTaskSideEffect {
    object EmptyFieldError : AddTaskSideEffect()
}
