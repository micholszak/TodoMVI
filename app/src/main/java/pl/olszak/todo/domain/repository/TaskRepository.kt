package pl.olszak.todo.domain.repository

import io.reactivex.Completable
import io.reactivex.Single

interface TaskRepository {

    fun addTask(task: Task): Completable

    fun getTasks(): Single<List<Task>>
}