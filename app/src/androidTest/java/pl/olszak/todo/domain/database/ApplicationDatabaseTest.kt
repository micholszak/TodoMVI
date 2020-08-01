package pl.olszak.todo.domain.database

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

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
    fun testTaskInsertion() {
        val entity = TaskEntity(title = "something")
        taskDao.insertTask(entity)
        val testObserver = taskDao.getAllTasks().test()
        val result = testObserver.values().last()
        assertThat(result).hasSize(1)
    }

    @Test
    fun taskAutoIncrementationTest() {
        val entities = List(5) { index ->
            TaskEntity(title = "title$index")
        }
        entities.forEach { entity ->
            taskDao.insertTask(entity)
        }
        val testObserver = taskDao.getAllTasks().test()
        val result = testObserver.values().last()
        assertThat(result).containsNoDuplicates()
    }
}
