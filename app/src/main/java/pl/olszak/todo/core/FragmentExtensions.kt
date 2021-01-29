package pl.olszak.todo.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> Fragment.bindToViewLifecycle(): ReadWriteProperty<Fragment, T> =
    ReadWriteLifecycleBoundProperty(this)

class ReadWriteLifecycleBoundProperty<T>(
    fragment: Fragment
) : ReadWriteProperty<Fragment, T>,
    DefaultLifecycleObserver {

    private var binding: T? = null
    private var viewLifecycleOwner: LifecycleOwner? = null

    init {
        fragment.viewLifecycleOwnerLiveData.observe(fragment, { newLifecycleOwner ->
            viewLifecycleOwner?.lifecycle?.removeObserver(this)
            viewLifecycleOwner = newLifecycleOwner.also {
                it.lifecycle.addObserver(this)
            }
        })
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        binding = null
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        this.binding = value
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return binding
            ?: throw IllegalStateException("You shouldn't use property before initialising it")
    }
}
