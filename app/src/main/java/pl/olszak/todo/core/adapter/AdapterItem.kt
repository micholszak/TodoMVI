package pl.olszak.todo.core.adapter

interface AdapterItem {

    fun areItemsTheSame(other: AdapterItem): Boolean

    fun areContentsTheSame(other: AdapterItem): Boolean
}