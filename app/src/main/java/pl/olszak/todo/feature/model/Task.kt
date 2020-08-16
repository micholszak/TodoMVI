package pl.olszak.todo.feature.model

import pl.olszak.todo.core.adapter.AdapterItem
import pl.olszak.todo.domain.database.model.Priority

data class Task(
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.LOW
) : AdapterItem {

    override fun areItemsTheSame(other: AdapterItem): Boolean =
        this == other

    override fun areContentsTheSame(other: AdapterItem): Boolean {
        val otherTask = other as? Task
            ?: return false

        return otherTask.title == title && otherTask.description == description
    }
}
