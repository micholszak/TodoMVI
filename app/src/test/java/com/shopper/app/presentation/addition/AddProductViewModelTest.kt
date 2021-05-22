package com.shopper.app.presentation.addition

import com.nhaarman.mockitokotlin2.mock
import com.shopper.cache.ProductCache
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.orbitmvi.orbit.assert
import org.orbitmvi.orbit.test
import com.shopper.app.domain.InstantTaskExecutorExtension
import com.shopper.app.domain.TestDispatcherProvider
import com.shopper.app.domain.interactor.AddProduct
import com.shopper.app.presentation.addition.model.AddTaskSideEffect
import com.shopper.app.presentation.addition.model.AddTaskViewState

@ExtendWith(InstantTaskExecutorExtension::class)
class AddProductViewModelTest {

    private val dispatcherProvider = TestDispatcherProvider()
    private val mockProductCache: ProductCache = mock()
    private val addTask = AddProduct(
        productCache = mockProductCache,
        dispatcherProvider = dispatcherProvider
    )

    @Test
    fun `Start with initial state`() {
        val initialState = AddTaskViewState.Idle
        val viewModel = AddTaskViewModel(addTask, dispatcherProvider)
            .test(AddTaskViewState.Idle)

        viewModel.assert(initialState)
    }

    @Test
    fun `Emit error event given that adding task fails`() = runBlockingTest {
        val initialState = AddTaskViewState.Idle
        val viewModel = AddTaskViewModel(addTask, dispatcherProvider)
            .test(initialState)
        viewModel.addTaskWith(name = "")
        viewModel.assert(initialState) {
            states(
                { AddTaskViewState.Pending },
                { AddTaskViewState.Idle }
            )
            postedSideEffects(
                AddTaskSideEffect.EmptyFieldError,
            )
        }
    }

    @Test
    fun `Indicate task added to view`() {
        val initialState = AddTaskViewState.Idle
        val viewModel = AddTaskViewModel(addTask, dispatcherProvider)
            .test(initialState)
        viewModel.addTaskWith("some name")
        viewModel.assert(initialState) {
            states(
                { AddTaskViewState.Pending },
                { AddTaskViewState.Added }
            )
        }
    }
}
