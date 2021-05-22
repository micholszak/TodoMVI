package com.shopper.app.presentation.addition.model

sealed class AddTaskViewState {
    object Idle : AddTaskViewState()
    object Pending : AddTaskViewState()
    object Added : AddTaskViewState()
}
