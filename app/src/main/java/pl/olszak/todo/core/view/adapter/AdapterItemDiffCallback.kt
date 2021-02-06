package pl.olszak.todo.core.view.adapter

import androidx.recyclerview.widget.DiffUtil
import pl.olszak.todo.core.view.model.AdapterItem

class AdapterItemDiffCallback : DiffUtil.ItemCallback<AdapterItem>() {

    override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
        oldItem.areItemsTheSame(newItem)

    override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
        oldItem.areContentsTheSame(newItem)
}
