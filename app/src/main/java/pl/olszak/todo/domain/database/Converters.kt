package pl.olszak.todo.domain.database

import androidx.room.TypeConverter
import pl.olszak.todo.domain.repository.Priority

class Converters {

    @TypeConverter
    fun fromPriority(priority: Priority): Int = priority.ordinal

    @TypeConverter
    fun toPriority(ordinal: Int): Priority = Priority.values()[ordinal]
}