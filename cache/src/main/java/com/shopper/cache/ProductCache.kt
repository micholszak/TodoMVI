package com.shopper.cache

import com.shopper.cache.model.CachedProduct
import com.shopper.cache.model.CheckedProduct
import kotlinx.coroutines.flow.Flow

interface ProductCache {

    fun getAllProducts(): Flow<List<CachedProduct>>

    suspend fun addProduct(productName: String)

    suspend fun updateCheckedProduct(product: CheckedProduct)
}
