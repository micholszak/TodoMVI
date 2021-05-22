package com.shopper.app.domain.interactor

import com.shopper.cache.ProductCache
import com.shopper.cache.model.CachedProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.shopper.app.domain.model.Task
import javax.inject.Inject

open class GetTasks @Inject constructor(
    private val productCache: ProductCache,
) {

    operator fun invoke(): Flow<List<Task>> =
        productCache.getAllProducts()
            .map(::mapEntities)

    private fun mapEntities(entities: List<CachedProduct>): List<Task> =
        entities.map { entity ->
            Task(
                title = entity.name
            )
        }
}
