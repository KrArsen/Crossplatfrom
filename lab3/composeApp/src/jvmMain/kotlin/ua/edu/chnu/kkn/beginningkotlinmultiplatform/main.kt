package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "BeginningKotlinMultiplatform",
    ) {
        App()
    }
}