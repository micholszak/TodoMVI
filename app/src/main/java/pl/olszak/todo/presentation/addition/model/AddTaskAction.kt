package pl.olszak.todo.presentation.addition.model

import pl.olszak.todo.domain.Action

sealed class AddTaskAction : Action {

    data class ProcessTask(val taskTitle: String) : AddTaskAction()
}
