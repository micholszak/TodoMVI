package pl.olszak.todo.domain

import android.view.View
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

@OptIn(ExperimentalCoroutinesApi::class)
fun View.clicks(): Flow<Unit> = callbackFlow {
    val listener = View.OnClickListener {
        offer(Unit)
    }
    invokeOnClose {
        setOnClickListener(null)
    }
    setOnClickListener(listener)
}.conflate()
