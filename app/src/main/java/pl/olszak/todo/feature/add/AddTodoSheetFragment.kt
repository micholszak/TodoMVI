package pl.olszak.todo.feature.add

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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus
import pl.olszak.todo.R
import pl.olszak.todo.core.clicks
import pl.olszak.todo.core.hideSoftInputFromDialog
import pl.olszak.todo.core.showSoftInputInDialog
import timber.log.Timber

@AndroidEntryPoint
class AddTodoSheetFragment : BottomSheetDialogFragment() {

    companion object {
        private const val THROTTLE_INTERVAL_MS = 200L
    }

    private val addTaskViewModel: AddTaskViewModel by viewModels()

    private lateinit var title: EditText
    private lateinit var createButton: Button
    private val taskIntent: Flow<AddTaskIntent> by lazy {
        addTaskIntent()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_add_todo_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view.findViewById(R.id.title)
        createButton = view.findViewById(R.id.create)
        addTaskViewModel.subscribeToIntent(taskIntent)
        addTaskViewModel.viewState.onEach { state ->
            render(state)
        }.launchIn(
            scope = lifecycleScope + CoroutineExceptionHandler { _, throwable ->
                Timber.e(throwable)
            }
        )
        title.showSoftInputInDialog()
    }

    @OptIn(FlowPreview::class)
    private fun addTaskIntent(): Flow<AddTaskIntent> =
        createButton.clicks()
            .debounce(THROTTLE_INTERVAL_MS)
            .map {
                AddTaskIntent.ProcessTask(title.text?.toString().orEmpty())
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
