package pl.olszak.todo.addition.model

import pl.olszak.todo.core.Intention

sealed class AddTaskIntent : Intention {

    data class ProcessTask(val taskTitle: String) : AddTaskIntent()
}
