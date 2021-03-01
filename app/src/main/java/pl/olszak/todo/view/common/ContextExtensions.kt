package pl.olszak.todo.view.common

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

inline fun <reified T> Context.systemService(): T? =
    ContextCompat.getSystemService(this, T::class.java)

fun Fragment.toast(message: String) {
    val context = context
    if (context != null) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
