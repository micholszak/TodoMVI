package pl.olszak.todo.core.view.model

interface AdapterItem {

    fun areItemsTheSame(other: AdapterItem): Boolean

    fun areContentsTheSame(other: AdapterItem): Boolean
}
