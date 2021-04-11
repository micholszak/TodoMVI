package com.shopper.cache.room.model

import androidx.room.ColumnInfo

internal data class CheckedProductEntity(
    @ColumnInfo(name = ProductTableInfo.COLUMN_ID)
    val productId: Long,
    @ColumnInfo(name = ProductTableInfo.COLUMN_CHECKED)
    val checked: Boolean,
)
