package com.shopper.cache.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.shopper.cache.room.model.CheckedProduct
import com.shopper.cache.room.model.ProductEntity
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class ProductsDaoTest {

    private lateinit var database: ShopperDatabase
    private lateinit var productsDao: ProductsDao

    @Before
    fun before() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShopperDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        productsDao = database.productsDao()
    }

    @After
    @Throws(IOException::class)
    fun after() {
        database.close()
    }

    @Test
    fun productAddsProperly() = runBlocking {
        val product = ProductEntity(name = "orange")
        productsDao.addProduct(product)
        productsDao.getProducts().test {
            val products = expectItem()
            assertThat(products).hasSize(1)
            assertThat(products[0].name).isEqualTo("orange")
        }
    }

    @Test
    fun thereShouldBeNoDuplicatesInProducts() = runBlocking {
        repeat(5) {
            val orange = ProductEntity(name = "orange")
            productsDao.addProduct(product = orange)
        }

        productsDao.getProducts().test {
            val products = expectItem()
            assertThat(products).doesNotHaveDuplicates()
        }
    }

    @Test
    fun daoShouldSendAnUpdateEveryTimeWhenProductIsAdded() = runBlocking {
        productsDao.getProducts().test {
            productsDao.addProduct(product = ProductEntity(name = "orange"))
            assertThat(expectItem()).hasSize(1)
            productsDao.addProduct(product = ProductEntity(name = "apple"))
            assertThat(expectItem()).hasSize(2)
        }
    }

    @Test
    fun changeCheckedStateOfRequestedItem() = runBlocking {
        productsDao.addProduct(product = ProductEntity(name = "orange"))
        productsDao.getProducts().test {
            val orange = expectItem().first()
            assertThat(orange.checked).isFalse
            productsDao.updateChecked(
                product = CheckedProduct(
                    productId = orange.productId,
                    checked = true
                )
            )
            val checkedOrange = expectItem().first()
            assertThat(checkedOrange.checked).isTrue
        }
    }
}
