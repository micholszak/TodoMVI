package com.shopper.cache.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    var productId: Long = 0L,
    val name: String = "",
    val checked: Boolean = false,
)
