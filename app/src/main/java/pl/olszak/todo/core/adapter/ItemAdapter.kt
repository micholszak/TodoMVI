package pl.olszak.todo.core.adapter

import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import pl.olszak.todo.core.view.animation.Animation
import pl.olszak.todo.core.view.animation.animateWith

class ItemAdapter(
    vararg delegates: AdapterDelegate<List<AdapterItem>>,
    private val animation: Animation? = null
) : AsyncListDifferDelegationAdapter<AdapterItem>(AdapterItemDiffCallback()) {

    init {
        delegates.forEach {
            delegatesManager.addDelegate(it)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.animateWith(animation)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any?>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.itemView.animateWith(animation)
    }
}
