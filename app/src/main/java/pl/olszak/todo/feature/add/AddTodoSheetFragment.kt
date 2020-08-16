package pl.olszak.todo.feature.add

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jakewharton.rxbinding3.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import pl.olszak.todo.R
import pl.olszak.todo.core.hideSoftInputFromDialog
import pl.olszak.todo.core.showSoftInputInDialog
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class AddTodoSheetFragment : BottomSheetDialogFragment() {

    companion object {
        private const val THROTTLE_INTERVAL_MS = 200L
    }

    private val addTaskViewModel: AddTaskViewModel by viewModels()

    private lateinit var title: EditText
    private lateinit var createButton: Button
    private val taskIntent: Observable<AddTaskIntent> by lazy {
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
        addTaskViewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        title.showSoftInputInDialog()
    }

    private fun addTaskIntent(): Observable<AddTaskIntent> =
        createButton.clicks()
            .throttleFirst(THROTTLE_INTERVAL_MS, TimeUnit.MILLISECONDS)
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
