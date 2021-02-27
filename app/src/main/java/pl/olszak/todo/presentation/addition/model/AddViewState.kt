package pl.olszak.todo.presentation.addition.model

import pl.olszak.todo.domain.ViewState
import pl.olszak.todo.view.common.model.ViewStateEvent

data class AddViewState(
    val isLoading: Boolean = false,
    val isTaskAdded: Boolean = false,
    val errorEvent: ViewStateEvent<FieldError>? = null
) : ViewState

enum class FieldError {
    TITLE
}
