package com.shopper.cache.internal.model

import androidx.room.ColumnInfo
import com.shopper.cache.internal.model.ProductTableInfo.COLUMN_CHECKED
import com.shopper.cache.internal.model.ProductTableInfo.COLUMN_NAME

data class AddProductEntity(
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_CHECKED)
    val checked: Boolean = false,
)