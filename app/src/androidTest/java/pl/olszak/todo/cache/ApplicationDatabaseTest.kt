package pl.olszak.todo.cache

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import pl.olszak.todo.cache.model.TaskEntity

class ApplicationDatabaseTest {

    private lateinit var database: ApplicationDatabase
    private lateinit var taskDao: TaskDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            ApplicationDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        taskDao = database.taskDao()
    }

    @Test
    fun testTaskInsertion() = runBlocking {
        val entity =
            TaskEntity(title = "something")
        taskDao.insertTask(entity)
        taskDao.getAllTasks().test {
            val tasks = expectItem()
            assertThat(tasks).hasSize(1)
        }
    }

    @Test
    fun taskAutoIncrementationTest() = runBlocking {
        val entities = List(5) {
            TaskEntity(title = "title")
        }
        entities.forEach { entity ->
            taskDao.insertTask(entity)
        }
        taskDao.getAllTasks().test {
            val tasks = expectItem()
            assertThat(tasks).doesNotHaveDuplicates()
        }
    }
}
