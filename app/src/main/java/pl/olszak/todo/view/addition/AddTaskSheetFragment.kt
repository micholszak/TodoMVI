package pl.olszak.todo.view.addition

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import pl.olszak.todo.R
import pl.olszak.todo.databinding.FragmentAddTaskSheetBinding
import pl.olszak.todo.presentation.addition.AddTaskViewModel
import pl.olszak.todo.presentation.addition.model.AddTaskSideEffect
import pl.olszak.todo.presentation.addition.model.AddTaskViewState
import pl.olszak.todo.view.common.binding.viewBinding
import pl.olszak.todo.view.common.hideSoftInputFromDialog
import pl.olszak.todo.view.common.showSoftInputInDialog
import pl.olszak.todo.view.common.toast

@AndroidEntryPoint
class AddTaskSheetFragment : BottomSheetDialogFragment() {

    private val binding: FragmentAddTaskSheetBinding by viewBinding(FragmentAddTaskSheetBinding::bind)
    private val addTaskViewModel: AddTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_add_task_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.showSoftInputInDialog()
        binding.createButton.setOnClickListener {
            val text = binding.title.text?.toString().orEmpty()
            addTaskViewModel.addTaskWith(text)
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            addTaskViewModel.container.stateFlow.collect(::render)
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            addTaskViewModel.container.sideEffectFlow.collect(::displaySideEffect)
        }
    }

    private fun render(state: AddTaskViewState) {
        when (state) {
            AddTaskViewState.Idle -> {
                binding.createButton.isClickable = true
            }
            AddTaskViewState.Pending -> {
                binding.createButton.isClickable = false
            }
            AddTaskViewState.Added -> {
                dismiss()
            }
        }
    }

    // todo extract proper error message, error should be cleared or moved to state handling
    private fun displaySideEffect(sideEffect: AddTaskSideEffect) {
        when (sideEffect) {
            AddTaskSideEffect.EmptyFieldError -> {
                toast("Title should not be empty")
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        binding.title.hideSoftInputFromDialog()
        super.onDismiss(dialog)
    }
}
