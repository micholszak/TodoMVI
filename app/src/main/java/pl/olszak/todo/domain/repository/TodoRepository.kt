package pl.olszak.todo.domain.repository

import io.reactivex.Single

interface TodoRepository {

    fun getTasks(): Single<List<Task>>
}