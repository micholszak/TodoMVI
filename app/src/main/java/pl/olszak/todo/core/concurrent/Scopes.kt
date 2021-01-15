package pl.olszak.todo.core.concurrent

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

interface DefaultCoroutineScope : CoroutineScope
interface MainCoroutineScope : CoroutineScope
interface IOCoroutineScope : CoroutineScope
interface MainImmediateCoroutineScope : CoroutineScope

fun defaultCoroutineScope(job: Job = SupervisorJob()): DefaultCoroutineScope =
    DefaultCoroutineScopeImpl(job)

fun mainCoroutineScope(job: Job = SupervisorJob()): MainCoroutineScope =
    MainCoroutineScopeImpl(job)

fun ioCoroutineScope(job: Job = SupervisorJob()): IOCoroutineScope =
    IOCoroutineScopeImpl(job)

fun mainImmediateCoroutineScope(job: Job = SupervisorJob()): MainImmediateCoroutineScope =
    MainImmediateCoroutineScopeImpl(job)

private class DefaultCoroutineScopeImpl(job: Job) : DefaultCoroutineScope {
    override val coroutineContext: CoroutineContext = job + Dispatchers.Default
}

private class MainCoroutineScopeImpl(job: Job) : MainCoroutineScope {
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main
}

private class IOCoroutineScopeImpl(job: Job) : IOCoroutineScope {
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO
}

private class MainImmediateCoroutineScopeImpl(job: Job) : MainImmediateCoroutineScope {
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main.immediate
}
