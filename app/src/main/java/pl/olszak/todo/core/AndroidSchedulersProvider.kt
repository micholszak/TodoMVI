package pl.olszak.todo.core

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class AndroidSchedulersProvider @Inject constructor() : SchedulersProvider {

    override fun main(): Scheduler = AndroidSchedulers.mainThread()
}