package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.shared.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window

@Composable
actual fun PlatformDialog(
    title: String,
    onCloseRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Window(onCloseRequest = onCloseRequest, title = title) {
        content()
    }
}
