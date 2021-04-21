package com.shopper.cache.internal.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo

@Keep
internal data class CheckedProductEntity(
    @ColumnInfo(name = ProductTableInfo.COLUMN_ID)
    val productId: Long,
    @ColumnInfo(name = ProductTableInfo.COLUMN_CHECKED)
    val checked: Boolean,
)
