package com.shopper.cache.internal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shopper.cache.internal.model.AddProductEntity
import com.shopper.cache.internal.model.CheckedProductEntity
import com.shopper.cache.internal.model.ProductEntity
import com.shopper.cache.internal.model.ProductTableInfo.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ProductDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE,
        entity = ProductEntity::class
    )
    suspend fun addProduct(entity: AddProductEntity)

    @Update(entity = ProductEntity::class)
    suspend fun updateChecked(entity: CheckedProductEntity)
}
