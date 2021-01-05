package pl.olszak.todo.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> Flow<T>.test(scope: CoroutineScope): TestObserver<T> {
    return TestObserver(scope, this)
}

class TestObserver<T>(
    scope: CoroutineScope,
    flow: Flow<T>
) {
    private val values = mutableListOf<T>()
    private val job: Job = scope.launch {
        flow.collect {
            values.add(it)
        }
    }

    fun assertValues(block: (list: List<T>) -> Unit): TestObserver<T> = apply {
        block(values.toList())
    }

    fun finish() {
        job.cancel()
    }
}
