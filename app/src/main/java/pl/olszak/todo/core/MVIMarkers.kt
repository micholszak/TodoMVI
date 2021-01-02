package pl.olszak.todo.core

typealias Reducer<S, R> = suspend (currentState: S, result: R) -> S

interface Intention

interface Result

interface ViewState
