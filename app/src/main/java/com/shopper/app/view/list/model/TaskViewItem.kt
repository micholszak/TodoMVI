package com.shopper.app.view.list.model

import com.shopper.app.view.common.model.AdapterItem

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
