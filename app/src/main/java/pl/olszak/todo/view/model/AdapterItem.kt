package pl.olszak.todo.view.model

interface AdapterItem {

    fun areItemsTheSame(other: AdapterItem): Boolean

    fun areContentsTheSame(other: AdapterItem): Boolean
}
