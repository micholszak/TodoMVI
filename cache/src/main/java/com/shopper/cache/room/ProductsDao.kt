package com.shopper.cache.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shopper.cache.room.model.CheckedProductEntity
import com.shopper.cache.room.model.ProductEntity
import com.shopper.cache.room.model.ProductTableInfo.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ProductsDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getProducts(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: ProductEntity)

    @Update(entity = ProductEntity::class)
    suspend fun updateChecked(productEntity: CheckedProductEntity)
}
