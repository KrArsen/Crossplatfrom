package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(title = "Beginning Kotlin Multiplatform") {
        App()
    }
}
