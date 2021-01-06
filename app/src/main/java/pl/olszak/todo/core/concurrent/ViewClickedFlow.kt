package pl.olszak.todo.core.concurrent

import android.view.View
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

fun View.clicks(): Flow<Unit> = callbackFlow {
    val listener = View.OnClickListener {
        offer(Unit)
    }
    setOnClickListener(listener)
    awaitClose {
        setOnClickListener(null)
    }
}.conflate()
