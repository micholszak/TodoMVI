package com.shopper.cache.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shopper.cache.room.model.ProductTableInfo.COLUMN_CHECKED
import com.shopper.cache.room.model.ProductTableInfo.COLUMN_ID
import com.shopper.cache.room.model.ProductTableInfo.COLUMN_NAME
import com.shopper.cache.room.model.ProductTableInfo.TABLE_NAME

internal object ProductTableInfo {
    const val TABLE_NAME = "PRODUCT"
    const val COLUMN_ID = "PRODUCT_ID"
    const val COLUMN_NAME = "NAME"
    const val COLUMN_CHECKED = "CHECKED"
}

@Entity(tableName = TABLE_NAME)
internal data class ProductEntity(
    @ColumnInfo(name = COLUMN_ID)
    @PrimaryKey(autoGenerate = true)
    var productId: Long = 0L,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String = "",
    @ColumnInfo(name = COLUMN_CHECKED)
    val checked: Boolean = false,
)
