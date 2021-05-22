package com.shopper.app.view.common.adapter

import androidx.recyclerview.widget.DiffUtil
import com.shopper.app.view.common.model.AdapterItem

class AdapterItemDiffCallback : DiffUtil.ItemCallback<AdapterItem>() {

    override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
        oldItem.areItemsTheSame(newItem)

    override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
        oldItem.areContentsTheSame(newItem)
}
