package pl.olszak.todo.domain.interactor

import android.database.SQLException
import com.shopper.cache.ProductCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import pl.olszak.todo.domain.DispatcherProvider
import pl.olszak.todo.domain.model.AddProductResult
import pl.olszak.todo.domain.model.Task
import javax.inject.Inject

class AddProduct @Inject constructor(
    private val productCache: ProductCache,
    private val dispatcherProvider: DispatcherProvider,
) {

    suspend operator fun invoke(task: Task): Flow<AddProductResult> = flow {
        if (task.title.isEmpty()) {
            emit(
                AddProductResult.Failure(
                    IllegalArgumentException("Task missing title")
                )
            )
            return@flow
        }
        try {
            productCache.addProduct(task.title)
            emit(AddProductResult.Success)
        } catch (e: SQLException) {
            emit(AddProductResult.Failure(e))
        }
    }.onStart {
        emit(AddProductResult.Pending)
    }.flowOn(dispatcherProvider.io)
}
