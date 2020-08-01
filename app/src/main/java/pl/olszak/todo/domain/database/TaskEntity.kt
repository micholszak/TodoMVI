package pl.olszak.todo.domain.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.olszak.todo.domain.repository.Priority

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val priority: Priority = Priority.LOW,
    val title: String = "",
    val description: String = ""
)
