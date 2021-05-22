package com.shopper.app.domain.interactor

import android.database.SQLException
import com.shopper.app.domain.DispatcherProvider
import com.shopper.app.domain.model.AddProductResult
import com.shopper.app.domain.model.Task
import com.shopper.cache.ProductCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
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
