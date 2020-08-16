package pl.olszak.todo.feature.add

import pl.olszak.todo.core.Intention

sealed class AddTaskIntent : Intention {

    data class ProcessTask(val taskTitle: String) : AddTaskIntent()
}