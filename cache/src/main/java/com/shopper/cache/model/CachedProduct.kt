package com.shopper.cache.model

data class CachedProduct(
    val productId: Long,
    val name: String,
    val checked: Boolean
)
