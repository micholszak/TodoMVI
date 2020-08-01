package pl.olszak.todo.domain.database

import androidx.room.TypeConverter
import pl.olszak.todo.domain.repository.Priority

object Converters {

    @JvmStatic
    @TypeConverter
    fun fromPriority(priority: Priority): Int = priority.ordinal

    @JvmStatic
    @TypeConverter
    fun toPriority(ordinal: Int): Priority = Priority.values()[ordinal]
}
