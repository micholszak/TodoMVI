package pl.olszak.todo.core

import kotlinx.coroutines.flow.Flow

typealias Reducer<S, R> = suspend (currentState: S, result: R) -> S

typealias IntentProcessor<S, R> = suspend (intent: S) -> Flow<R>

interface Intention

interface Result

interface ViewState
