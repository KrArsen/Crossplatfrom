package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

actual fun showDialog(
    title: String,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    var dialogData: DialogData? = null
    val controller = object : DialogController {
        override fun close() {
            onDismiss()
            activeWebDialogs.remove(dialogData)
        }
    }
    dialogData = DialogData(title, onDismiss) {
        CompositionLocalProvider(LocalDialogController provides controller) {
            content()
        }
    }
    activeWebDialogs.add(dialogData)
}
