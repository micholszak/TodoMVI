package pl.olszak.todo.feature.add

import pl.olszak.todo.core.Result

sealed class AddTaskResult : Result {

    object Pending : AddTaskResult()

    object Added : AddTaskResult()

    object Failure : AddTaskResult()
}
