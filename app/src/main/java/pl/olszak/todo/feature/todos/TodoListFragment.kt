package pl.olszak.todo.feature.todos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import pl.olszak.todo.R
import pl.olszak.todo.core.adapter.ItemAdapter
import pl.olszak.todo.core.animation.ScaleAnimation
import pl.olszak.todo.feature.todos.adapter.TaskViewItem
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
        addTask.setOnClickListener {
            findNavController().navigate(R.id.action_add_todo)
        }
    }

    private fun setupList() {
        listAdapter = ItemAdapter(
            createTaskDelegate(),
            animation = ScaleAnimation()
        )
        taskList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter
            itemAnimator = FadeInUpAnimator().apply {
                addDuration = 200
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val list = List(30) { index ->
            TaskViewItem(
                title = "title$index",
                description = "description$index"
            )
        }

        disposable = Observable.just(list).delay(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { items ->
                listAdapter.items = items
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        disposable = null
    }
}
