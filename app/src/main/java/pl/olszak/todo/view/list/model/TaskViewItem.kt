package pl.olszak.todo.view.list.model

import pl.olszak.todo.view.common.model.AdapterItem

data class TaskViewItem(
    val title: String = "",
    val description: String = ""
) : AdapterItem {

    override fun areItemsTheSame(other: AdapterItem): Boolean =
        this == other

    override fun areContentsTheSame(other: AdapterItem): Boolean {
        val otherTask = other as? TaskViewItem
            ?: return false

        return otherTask.title == title &&
            otherTask.description == description
    }
}
