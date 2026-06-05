package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.mutableStateListOf

expect fun openDialog(
    title: String,
    content: @Composable () -> Unit
)

interface DialogController {
    fun close()
}

val LocalDialogController = staticCompositionLocalOf<DialogController> {
    error("No DialogController provided")
}

data class WebDialogData(val title: String, val content: @Composable () -> Unit)

val activeWebDialogs = mutableStateListOf<WebDialogData>()
