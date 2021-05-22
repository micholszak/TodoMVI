package com.shopper.app.presentation.list

import androidx.lifecycle.ViewModel
import com.shopper.app.domain.DispatcherProvider
import com.shopper.app.domain.interactor.GetTasks
import com.shopper.app.domain.model.Task
import com.shopper.app.presentation.list.model.TasksViewState
import com.shopper.app.view.list.model.TaskViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasks: GetTasks,
    dispatcherProvider: DispatcherProvider,
) : ViewModel(), ContainerHost<TasksViewState, Unit> {

    override val container: Container<TasksViewState, Unit> =
        container(
            initialState = TasksViewState(),
            settings = Container.Settings(
                backgroundDispatcher = dispatcherProvider.io,
                orbitDispatcher = dispatcherProvider.default
            )
        ) {
            subscribeToDatabaseUpdates()
        }

    private fun subscribeToDatabaseUpdates() = intent {
        getTasks().map(::mapTasks).collect { items ->
            reduce { state.copy(tasks = items) }
        }
    }

    private fun mapTasks(tasks: List<Task>): List<TaskViewItem> {
        return tasks.map { task ->
            TaskViewItem(
                title = task.title,
            )
        }
    }
}
