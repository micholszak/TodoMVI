package pl.olszak.todo.feature.todos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import pl.olszak.todo.R
import pl.olszak.todo.core.adapter.AdapterItemDiffCallback
import pl.olszak.todo.core.adapter.ItemAdapter
import pl.olszak.todo.domain.repository.Priority
import pl.olszak.todo.domain.repository.Task
import pl.olszak.todo.feature.todos.adapter.createTaskDelegate
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class TodoListFragment : Fragment() {

    private lateinit var taskList: RecyclerView
    private lateinit var addTask: FloatingActionButton
    private lateinit var toolbar: Toolbar
    private lateinit var listAdapter: ItemAdapter

    private var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_todo_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskList = view.findViewById(R.id.list)
        addTask = view.findViewById(R.id.add_task)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_todo_list)

        setupList()
    }

    private fun setupList() {
        listAdapter = ItemAdapter(
            createTaskDelegate()
        )
        taskList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter.apply {
                items = emptyList()
            }
            itemAnimator = FadeInUpAnimator().apply {
                addDuration = 200
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val list = List(30) { index ->
            Task(
                title = "title$index",
                description = "description$index",
                priority = Priority.LOW
            )
        }

        disposable = Observable.fromIterable(list).delay(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { item ->
                val items = listAdapter.items
                listAdapter.items = items.plus(item)
                listAdapter.notifyItemInserted(items.size)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        disposable = null
    }
}