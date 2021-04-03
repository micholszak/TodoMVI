package pl.olszak.todo.view.common

import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.showSoftInputInDialog() {
    if (isFocusable) {
        if (requestFocus()) {
            val inputManager: InputMethodManager? = context.systemService()
            inputManager?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }
}

fun View.hideSoftInputFromDialog() {
    clearFocus()
    val inputManager: InputMethodManager? = context.systemService()
    inputManager?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}
