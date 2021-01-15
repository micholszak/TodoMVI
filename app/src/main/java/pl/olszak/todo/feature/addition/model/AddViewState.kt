package pl.olszak.todo.feature.addition.model

import pl.olszak.todo.core.ViewState
import pl.olszak.todo.core.ViewStateEvent

data class AddViewState(
    val isLoading: Boolean = false,
    val isTaskAdded: Boolean = false,
    val errorEvent: ViewStateEvent<FieldError>? = null
) : ViewState

enum class FieldError {
    TITLE
}
