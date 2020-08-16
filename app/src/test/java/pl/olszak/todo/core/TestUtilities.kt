package pl.olszak.todo.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.spy

fun <T> LiveData<T>.emissions(): List<T> {
    val list: MutableList<T> = mutableListOf()
    val spy: Observer<T> = spy(Observer {
        list.add(it)
    })
    observeForever(spy)
    return list
}