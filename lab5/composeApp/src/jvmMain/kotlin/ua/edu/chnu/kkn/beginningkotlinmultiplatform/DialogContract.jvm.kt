package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

actual fun openDialog(
    title: String,
    content: @Composable () -> Unit
) {
    java.lang.Thread {
        application {
            val controller = remember {
                object : DialogController {
                    override fun close() {
                        exitApplication()
                    }
                }
            }
            Window(
                onCloseRequest = { controller.close() },
                title = title
            ) {
                CompositionLocalProvider(LocalDialogController provides controller) {
                    content()
                }
            }
        }
    }.start()
}
