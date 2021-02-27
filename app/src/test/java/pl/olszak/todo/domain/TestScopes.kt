package pl.olszak.todo.core.domain

import kotlinx.coroutines.test.TestCoroutineScope
import pl.olszak.todo.domain.DefaultCoroutineScope
import pl.olszak.todo.domain.IOCoroutineScope
import pl.olszak.todo.domain.MainCoroutineScope
import pl.olszak.todo.domain.MainImmediateCoroutineScope
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
