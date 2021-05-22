package com.shopper.app.domain.interactor

import app.cash.turbine.test
import com.shopper.cache.ProductCache
import com.shopper.cache.model.CachedProduct
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetTasksTest {
    private val mockProductCache: ProductCache = mock()

    private val getTasks = GetTasks(
        productCache = mockProductCache
    )

    @Test
    fun `Retrieve tasks from repository`() = runBlockingTest {
        givenGetAllProductsOperate()

        getTasks().test {
            val tasks = expectItem()
            assertThat(tasks).hasSize(0)
            expectComplete()
        }
    }

    @Test
    fun `Map entities to tasks`() = runBlockingTest {
        val title = "SCRUM"
        val entities = List(5) { index ->
            CachedProduct(
                productId = index.toLong(),
                name = title,
                checked = false
            )
        }

        givenGetAllProductsOperate(returnedProducts = entities)
        getTasks().test {
            val tasks = expectItem()
            tasks.forEach { task ->
                assertThat(task.title).isEqualTo(title)
            }
            expectComplete()
        }
    }

    private fun givenGetAllProductsOperate(returnedProducts: List<CachedProduct> = emptyList()) {
        whenever(mockProductCache.getAllProducts()).doReturn(flowOf(returnedProducts))
    }
}
