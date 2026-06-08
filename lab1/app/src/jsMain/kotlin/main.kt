package com.example.myapplication

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(title = "My KMP Application", canvasElementId = "compose-target") {
        App()
    }
}
