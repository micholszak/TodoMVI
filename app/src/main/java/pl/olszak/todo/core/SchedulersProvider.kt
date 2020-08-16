package pl.olszak.todo.core

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

interface SchedulersProvider {

    fun main(): Scheduler

    fun io(): Scheduler = Schedulers.io()

    fun computation(): Scheduler = Schedulers.computation()
}