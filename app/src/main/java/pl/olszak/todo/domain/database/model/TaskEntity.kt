package pl.olszak.todo.domain.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val priority: Priority = Priority.LOW,
    val title: String = "",
    val description: String = ""
)
