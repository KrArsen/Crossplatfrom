package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateListOf

data class DesktopDialogData(
    val title: String,
    val onDismiss: () -> Unit,
    val content: @Composable () -> Unit
)

val activeDesktopDialogs = mutableStateListOf<DesktopDialogData>()

actual fun showDialog(
    title: String,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    var dialogData: DesktopDialogData? = null
    val controller = object : DialogController {
        override fun close() {
            onDismiss()
            activeDesktopDialogs.remove(dialogData)
        }
    }
    dialogData = DesktopDialogData(title, onDismiss) {
        CompositionLocalProvider(LocalDialogController provides controller) {
            content()
        }
    }
    activeDesktopDialogs.add(dialogData)
}
