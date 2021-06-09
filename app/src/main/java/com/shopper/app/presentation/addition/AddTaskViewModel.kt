package com.shopper.app.presentation.addition

import androidx.lifecycle.ViewModel
import com.shopper.app.presentation.addition.model.AddTaskSideEffect
import com.shopper.app.presentation.addition.model.AddTaskViewState
import com.shopper.domain.DispatcherProvider
import com.shopper.domain.interactor.AddProduct
import com.shopper.domain.model.AddProductResult
import com.shopper.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addProduct: AddProduct,
    dispatcherProvider: DispatcherProvider,
) : ViewModel(), ContainerHost<AddTaskViewState, AddTaskSideEffect> {

    override val container: Container<AddTaskViewState, AddTaskSideEffect> =
        container(
            initialState = AddTaskViewState.Idle,
            settings = Container.Settings(
                backgroundDispatcher = dispatcherProvider.io,
                orbitDispatcher = dispatcherProvider.default,
            )
        )

    // todo why can't I post side effect in reduce block?
    fun addProductWith(name: String) = intent {
        reduce {
            AddTaskViewState.Pending
        }
        val product = Product(name)
        val result = addProduct.execute(product)
        reduce {
            when (result) {
                is AddProductResult.Success -> AddTaskViewState.Added
                is AddProductResult.Failure -> AddTaskViewState.Idle
            }
        }
        if (result is AddProductResult.Failure) {
            postSideEffect(AddTaskSideEffect.EmptyFieldError)
        }
    }
}
