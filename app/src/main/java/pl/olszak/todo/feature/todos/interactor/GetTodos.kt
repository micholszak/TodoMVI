package pl.olszak.todo.feature.todos.interactor

import kotlinx.coroutines.flow.Flow
import pl.olszak.todo.domain.database.model.TaskEntity
import javax.inject.Inject

class GetTodos @Inject constructor() {

    suspend operator fun invoke(): Flow<TaskEntity> {
        TODO("not yet implemented")
    }
}
