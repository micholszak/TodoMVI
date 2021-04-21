package pl.olszak.todo.domain.interactor

import android.database.SQLException
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shopper.cache.ProductCache
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.olszak.todo.domain.TestDispatcherProvider
import pl.olszak.todo.domain.model.AddProductResult
import pl.olszak.todo.domain.model.Task

class AddProductTest {
    private val mockProductCache: ProductCache = mock()
    private val dispatcherProvider = TestDispatcherProvider()

    private val addProduct = AddProduct(
        productCache = mockProductCache,
        dispatcherProvider = dispatcherProvider
    )

    @Test
    fun `Start with pending state`() = runBlocking {
        val task = Task()
        addProduct(task).test {
            assertThat(AddProductResult.Pending).isEqualTo(expectItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Fail the addition on empty title with IllegalArgumentException`() = runBlocking {
        val task = Task()
        addProduct(task = task).test {
            assertThat(AddProductResult.Pending).isEqualTo(expectItem())
            val result = expectItem()
            val failure = result as AddProductResult.Failure
            assertThat(failure.throwable).isInstanceOf(IllegalArgumentException::class.java)
            expectComplete()
        }
    }

    @Test
    fun `Succeed the addition given the task is valid`() = runBlocking {
        val task = Task(
            title = "something"
        )
        addProduct(task = task).test {
            assertThat(AddProductResult.Pending).isEqualTo(expectItem())
            verify(mockProductCache).addProduct(task.title)
            assertThat(AddProductResult.Success).isEqualTo(expectItem())
            expectComplete()
        }
    }

    @Test
    fun `Fail the addition when service fails to process the request`() = runBlockingTest {
        whenever(mockProductCache.addProduct(any())).doThrow(SQLException())

        val task = Task(title = "not an empty title")
        addProduct(task = task).test {
            assertThat(AddProductResult.Pending).isEqualTo(expectItem())
            val failure = expectItem() as AddProductResult.Failure
            assertThat(failure.throwable).isInstanceOf(SQLException::class.java)
            expectComplete()
        }
    }
}
