package com.shopper.domain.interactor

import com.shopper.domain.model.AddProductResult
import com.shopper.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface AddProduct {

    suspend fun execute(product: Product): AddProductResult
}
