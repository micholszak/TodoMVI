package pl.olszak.todo.todos.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import pl.olszak.todo.R
import pl.olszak.todo.core.adapter.ItemAdapter
import pl.olszak.todo.core.view.animation.ScaleAnimation
import pl.olszak.todo.core.viewBinding
import pl.olszak.todo.databinding.FragmentTodoListBinding
import pl.olszak.todo.todos.TodosViewModel
import pl.olszak.todo.todos.model.TodosIntent
import pl.olszak.todo.todos.model.TodosViewState
import kotlin.time.seconds

@AndroidEntryPoint
class TodoListFragment : Fragment() {

    private var listAdapter: ItemAdapter? = null
    private val binding: FragmentTodoListBinding by viewBinding(FragmentTodoListBinding::bind)

    private val viewModel: TodosViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.menu_todo_list)
        setupList()
        binding.addTask.setOnClickListener {
            findNavController().navigate(R.id.action_add_todo)
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect(::render)
        }
    }

    private fun setupList() {
        listAdapter = ItemAdapter(
            createTaskDelegate(),
            animation = ScaleAnimation()
        )
        binding.list.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter
            itemAnimator = FadeInUpAnimator().apply {
                addDuration = 200
            }
        }
    }

    private fun render(state: TodosViewState) {
        listAdapter?.items = state.tasks
    }
}
