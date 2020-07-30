package pl.olszak.todo.core.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ItemAdapter(
    vararg delegates: AdapterDelegate<List<AdapterItem>>
) : AsyncListDifferDelegationAdapter<AdapterItem>(AdapterItemDiffCallback()) {

    init {
        delegates.forEach(delegatesManager::addDelegate)
    }
}