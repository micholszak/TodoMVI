package pl.olszak.todo.feature.todos

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import pl.olszak.todo.feature.todos.interactor.GetTodos

class TodosViewModel @ViewModelInject constructor(
    private val getTodos: GetTodos
) : ViewModel()
