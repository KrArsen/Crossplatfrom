package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

actual fun openDialog(
    title: String,
    content: @Composable () -> Unit
) {
    var dialogData: WebDialogData? = null
    val controller = object : DialogController {
        override fun close() {
            activeWebDialogs.remove(dialogData)
        }
    }
    dialogData = WebDialogData(title) {
        CompositionLocalProvider(LocalDialogController provides controller) {
            content()
        }
    }
    activeWebDialogs.add(dialogData)
}
