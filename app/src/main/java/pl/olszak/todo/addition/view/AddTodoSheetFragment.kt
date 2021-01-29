package pl.olszak.todo.addition.view

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
import pl.olszak.todo.addition.AddTaskViewModel
import pl.olszak.todo.addition.model.AddTaskIntent
import pl.olszak.todo.addition.model.AddViewState
import pl.olszak.todo.addition.model.FieldError
import pl.olszak.todo.core.bindToViewLifecycle
import pl.olszak.todo.core.hideSoftInputFromDialog
import pl.olszak.todo.core.showSoftInputInDialog
import pl.olszak.todo.databinding.FragmentAddTodoSheetBinding
import reactivecircus.flowbinding.android.view.clicks

@AndroidEntryPoint
class AddTodoSheetFragment : BottomSheetDialogFragment() {

    companion object {
        private const val THROTTLE_INTERVAL_MS = 200L
    }

    private var binding: FragmentAddTodoSheetBinding by bindToViewLifecycle()
    private val addTaskViewModel: AddTaskViewModel by viewModels()
    private val intents: Flow<AddTaskIntent> by lazy {
        binding.createButton.clicks()
            .debounce(THROTTLE_INTERVAL_MS)
            .map {
                val text = binding.title.text?.toString().orEmpty()
                AddTaskIntent.ProcessTask(text)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTodoSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTaskViewModel.subscribeToIntents(intents)
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
