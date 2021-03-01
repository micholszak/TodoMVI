package pl.olszak.todo.view.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import kotlinx.coroutines.flow.collect
import pl.olszak.todo.R
import pl.olszak.todo.databinding.FragmentTaskListBinding
import pl.olszak.todo.presentation.list.TasksViewModel
import pl.olszak.todo.presentation.list.model.TasksViewState
import pl.olszak.todo.view.common.ScaleAnimation
import pl.olszak.todo.view.common.adapter.ItemAdapter
import pl.olszak.todo.view.common.binding.viewBinding
import pl.olszak.todo.view.list.adapter.createTaskDelegate

@AndroidEntryPoint
class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private var listAdapter: ItemAdapter? = null
    private val binding: FragmentTaskListBinding by viewBinding(FragmentTaskListBinding::bind)

    private val viewModel: TasksViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.menu_todo_list)
        setupList()
        binding.addTask.setOnClickListener {
            findNavController().navigate(R.id.action_add_task)
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.container.stateFlow.collect(::render)
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

    private fun render(state: TasksViewState) {
        listAdapter?.items = state.tasks
    }
}
