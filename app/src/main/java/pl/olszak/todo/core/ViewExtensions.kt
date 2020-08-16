package pl.olszak.todo.core

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

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

inline fun <reified T> Context.systemService(): T? =
    ContextCompat.getSystemService(this, T::class.java)
