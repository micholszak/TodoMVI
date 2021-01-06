package pl.olszak.todo.feature.addition.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import pl.olszak.todo.R
import pl.olszak.todo.core.concurrent.clicks
import pl.olszak.todo.core.hideSoftInputFromDialog
import pl.olszak.todo.core.showSoftInputInDialog
import pl.olszak.todo.feature.addition.AddTaskViewModel
import pl.olszak.todo.feature.addition.model.AddTaskIntent
import pl.olszak.todo.feature.addition.model.AddViewState
import pl.olszak.todo.feature.addition.model.FieldError

@AndroidEntryPoint
class AddTodoSheetFragment : BottomSheetDialogFragment() {

    companion object {
        private const val THROTTLE_INTERVAL_MS = 200L
    }

    private val addTaskViewModel: AddTaskViewModel by viewModels()
    private val intents: Flow<AddTaskIntent> by lazy {
        createButton.clicks()
            .debounce(THROTTLE_INTERVAL_MS)
            .map {
                AddTaskIntent.ProcessTask(title.text?.toString().orEmpty())
            }
    }

    private lateinit var title: EditText
    private lateinit var createButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_add_todo_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view.findViewById(R.id.title)
        createButton = view.findViewById(R.id.createButton)
        addTaskViewModel.subscribeToIntents(intents)
        addTaskViewModel.state.onEach(::render)
            .launchIn(lifecycleScope)
        title.showSoftInputInDialog()
    }

    private fun render(state: AddViewState) {
        createButton.isClickable = state.isLoading.not()
        state.errorEvent?.consume { field ->
            when (field) {
                FieldError.TITLE -> title.error = "Title should not be empty"
            }
        }

        if (state.isTaskAdded) {
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        title.hideSoftInputFromDialog()
        super.onDismiss(dialog)
    }
}
