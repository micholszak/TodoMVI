package pl.olszak.todo.feature.todos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import kotlinx.coroutines.flow.collect
import pl.olszak.todo.R
import pl.olszak.todo.core.adapter.ItemAdapter
import pl.olszak.todo.core.animation.ScaleAnimation
import pl.olszak.todo.core.bindToViewLifecycle
import pl.olszak.todo.databinding.FragmentTodoListBinding
import pl.olszak.todo.feature.todos.adapter.createTaskDelegate
import pl.olszak.todo.feature.todos.model.TodosViewState

@AndroidEntryPoint
class TodoListFragment : Fragment() {

    private lateinit var listAdapter: ItemAdapter
    private var binding: FragmentTodoListBinding by bindToViewLifecycle()

    private val viewModel: TodosViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTodoListBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

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
        listAdapter.items = state.tasks
    }
}
