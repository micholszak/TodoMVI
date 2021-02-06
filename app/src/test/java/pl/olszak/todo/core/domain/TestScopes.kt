package pl.olszak.todo.core.domain

import kotlinx.coroutines.test.TestCoroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface CompositeTestCoroutineScope :
    TestCoroutineScope,
    MainCoroutineScope,
    IOCoroutineScope,
    DefaultCoroutineScope,
    MainImmediateCoroutineScope

private class CompositeTestCoroutineScopeImpl(
    context: CoroutineContext
) : CompositeTestCoroutineScope, TestCoroutineScope by TestCoroutineScope(context)

fun compositeTestCoroutineScope(
    context: CoroutineContext = EmptyCoroutineContext
): CompositeTestCoroutineScope = CompositeTestCoroutineScopeImpl(context = context)
