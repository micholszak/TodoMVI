package com.shopper.cache.internal.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shopper.cache.internal.model.ProductTableInfo.COLUMN_CHECKED
import com.shopper.cache.internal.model.ProductTableInfo.COLUMN_ID
import com.shopper.cache.internal.model.ProductTableInfo.COLUMN_NAME
import com.shopper.cache.internal.model.ProductTableInfo.TABLE_NAME

@Keep
@Entity(tableName = TABLE_NAME)
internal data class ProductEntity(
    @ColumnInfo(name = COLUMN_ID)
    @PrimaryKey(autoGenerate = true)
    val productId: Long,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_CHECKED)
    val checked: Boolean,
)
