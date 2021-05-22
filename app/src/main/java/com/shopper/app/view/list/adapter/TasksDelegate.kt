package com.shopper.app.view.list.adapter

import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.shopper.app.R
import com.shopper.app.databinding.ViewHolderTaskBinding
import com.shopper.app.view.common.model.AdapterItem
import com.shopper.app.view.list.model.TaskViewItem

fun createTaskDelegate() =
    adapterDelegateViewBinding<TaskViewItem, AdapterItem, ViewHolderTaskBinding>(
        { layoutInflater, root ->
            ViewHolderTaskBinding.inflate(layoutInflater, root, false)
        }
    ) {
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)

        bind {
            title.text = item.title
            description.text = item.description
        }
    }
