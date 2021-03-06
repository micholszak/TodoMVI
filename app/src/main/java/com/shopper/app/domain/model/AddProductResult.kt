package com.shopper.app.domain.model

sealed class AddProductResult {
    object Pending : AddProductResult()
    object Success : AddProductResult()
    data class Failure(val throwable: Throwable) : AddProductResult()
}
