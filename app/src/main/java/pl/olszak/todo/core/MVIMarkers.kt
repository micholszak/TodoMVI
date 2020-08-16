package pl.olszak.todo.core

typealias Reducer<S, R> = (currentState: S, result: R) -> S

interface Intention

interface Result

interface ViewState
