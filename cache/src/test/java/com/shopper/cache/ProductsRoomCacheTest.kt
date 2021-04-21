package com.shopper.cache

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shopper.cache.internal.ProductDao
import com.shopper.cache.internal.model.AddProductEntity
import com.shopper.cache.internal.model.CheckedProductEntity
import com.shopper.cache.internal.model.ProductEntity
import com.shopper.cache.model.CheckedProduct
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ProductsRoomCacheTest {

    private val mockProductDao: ProductDao = mock()
    private val productCache = ProductRoomCache(
        productDao = mockProductDao
    )

    @Test
    fun `Get all products and map them from cache`() = runBlocking {
        val size = 10
        val entities = List(size) { index ->
            ProductEntity(
                productId = index.toLong(),
                name = "orange",
                checked = false
            )
        }
        whenever(mockProductDao.getAllProducts()).thenReturn(flowOf(entities))

        productCache.getAllProducts().test {
            val cachedProducts = expectItem()
            assertThat(cachedProducts).hasSize(size)
            assertThat(cachedProducts).allMatch { cachedProduct ->
                !cachedProduct.checked && cachedProduct.name == "orange"
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Add product to database`() = runBlocking {
        val productName = "orange"
        productCache.addProduct(productName)
        val expectedEntity = AddProductEntity(
            name = productName,
            checked = false
        )
        verify(mockProductDao).addProduct(expectedEntity)
    }

    @Test
    fun `Change the checked state of product using Dao`() = runBlocking {
        val product = CheckedProduct(
            productId = 100,
            checked = true
        )
        val expectedEntity = CheckedProductEntity(
            productId = product.productId,
            checked = product.checked
        )
        productCache.updateCheckedProduct(product)
        verify(mockProductDao).updateChecked(expectedEntity)
    }
}
