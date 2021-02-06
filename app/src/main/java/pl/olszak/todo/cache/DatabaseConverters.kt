package pl.olszak.todo.cache

import androidx.room.TypeConverter
import pl.olszak.todo.cache.model.Priority

object DatabaseConverters {

    @JvmStatic
    @TypeConverter
    fun fromPriority(priority: Priority): Int = priority.ordinal

    @JvmStatic
    @TypeConverter
    fun toPriority(ordinal: Int): Priority = Priority.values()[ordinal]
}
