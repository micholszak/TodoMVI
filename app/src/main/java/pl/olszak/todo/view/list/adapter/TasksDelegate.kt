package pl.olszak.todo.view.list.adapter

import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import pl.olszak.todo.R
import pl.olszak.todo.databinding.ViewHolderTaskBinding
import pl.olszak.todo.view.list.model.TaskViewItem
import pl.olszak.todo.view.model.AdapterItem

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
