package pl.olszak.todo.core.adapter

import androidx.recyclerview.widget.DiffUtil

class AdapterItemDiffCallback : DiffUtil.ItemCallback<AdapterItem>() {

    override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
        oldItem.areItemsTheSame(newItem)

    override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
        oldItem.areContentsTheSame(newItem)
}