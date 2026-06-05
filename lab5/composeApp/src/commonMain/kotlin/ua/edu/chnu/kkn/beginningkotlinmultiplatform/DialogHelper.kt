package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.mutableStateListOf

expect fun showDialog(
    title: String,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
)

interface DialogController {
    fun close()
}

val LocalDialogController = staticCompositionLocalOf<DialogController> {
    error("No DialogController provided")
}

data class DialogData(
    val title: String,
    val onDismiss: () -> Unit,
    val content: @Composable () -> Unit
)

val activeWebDialogs = mutableStateListOf<DialogData>()
