package pl.olszak.todo.feature.add

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import pl.olszak.todo.R
import pl.olszak.todo.core.hideSoftInputFromDialog
import pl.olszak.todo.core.showSoftInputInDialog

@AndroidEntryPoint
class AddTodoSheetFragment : BottomSheetDialogFragment() {

    private lateinit var title: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_todo_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view.findViewById(R.id.title)
    }

    override fun onStart() {
        super.onStart()
        title.showSoftInputInDialog()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        title.hideSoftInputFromDialog()
    }
}
