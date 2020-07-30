package pl.olszak.todo.domain.repository

import pl.olszak.todo.core.adapter.AdapterItem

data class Task(
    val title: String,
    val description: String,
    val priority: Priority
) : AdapterItem {

    override fun areItemsTheSame(other: AdapterItem): Boolean =
        this == other

    override fun areContentsTheSame(other: AdapterItem): Boolean {
        val otherTask = other as? Task ?: return false

        return otherTask.title == title && otherTask.description == description
    }
}