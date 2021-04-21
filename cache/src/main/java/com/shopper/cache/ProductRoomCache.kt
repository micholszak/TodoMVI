package com.shopper.cache

import com.shopper.cache.internal.ProductDao
import com.shopper.cache.internal.model.AddProductEntity
import com.shopper.cache.internal.model.CheckedProductEntity
import com.shopper.cache.internal.model.ProductEntity
import com.shopper.cache.model.CachedProduct
import com.shopper.cache.model.CheckedProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ProductRoomCache(
    private val productDao: ProductDao
) : ProductCache {

    override fun getAllProducts(): Flow<List<CachedProduct>> =
        productDao.getAllProducts()
            .map { products -> products.map(::mapProductEntity) }

    override suspend fun addProduct(productName: String) {
        val entity = AddProductEntity(
            name = productName
        )
        productDao.addProduct(entity)
    }

    override suspend fun updateCheckedProduct(product: CheckedProduct) {
        val checkedProductEntity = CheckedProductEntity(
            productId = product.productId,
            checked = product.checked
        )
        productDao.updateChecked(checkedProductEntity)
    }

    private fun mapProductEntity(entity: ProductEntity): CachedProduct =
        CachedProduct(
            productId = entity.productId,
            name = entity.name,
            checked = entity.checked
        )
}
