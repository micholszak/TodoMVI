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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import pl.olszak.todo.R
import pl.olszak.todo.databinding.FragmentAddTodoSheetBinding
import pl.olszak.todo.presentation.addition.AddTaskViewModel
import pl.olszak.todo.presentation.addition.model.AddTaskAction
import pl.olszak.todo.presentation.addition.model.AddViewState
import pl.olszak.todo.presentation.addition.model.FieldError
import pl.olszak.todo.view.hideSoftInputFromDialog
import pl.olszak.todo.view.showSoftInputInDialog
import pl.olszak.todo.view.viewBinding
import reactivecircus.flowbinding.android.view.clicks

@AndroidEntryPoint
class AddTodoSheetFragment : BottomSheetDialogFragment() {

    companion object {
        private const val THROTTLE_INTERVAL_MS = 200L
    }

    private val binding: FragmentAddTodoSheetBinding by viewBinding(FragmentAddTodoSheetBinding::bind)
    private val addTaskViewModel: AddTaskViewModel by viewModels()
    private val actions: Flow<AddTaskAction> by lazy {
        binding.createButton.clicks()
            .debounce(THROTTLE_INTERVAL_MS)
            .map {
                val text = binding.title.text?.toString().orEmpty()
                AddTaskAction.ProcessTask(text)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_add_todo_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTaskViewModel.subscribeToActions(actions)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            addTaskViewModel.state.collect(::render)
        }
        binding.title.showSoftInputInDialog()
    }

    private fun render(state: AddViewState) {
        binding.createButton.isClickable = state.isLoading.not()
        state.errorEvent?.consume { field ->
            when (field) {
                FieldError.TITLE -> binding.title.error = "Title should not be empty"
            }
        }

        if (state.isTaskAdded) {
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        binding.title.hideSoftInputFromDialog()
        super.onDismiss(dialog)
    }
}
