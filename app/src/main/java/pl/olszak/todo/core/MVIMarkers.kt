package pl.olszak.todo.core

import kotlinx.coroutines.flow.Flow

typealias Reducer<State, Result> = suspend (currentState: State, result: Result) -> State

typealias ActionProcessor<Action, Result> = suspend (intent: Action) -> Flow<Result>

interface Action

interface Result

interface ViewState
