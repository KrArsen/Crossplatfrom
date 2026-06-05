package com.example.lab6

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Lab 6: System Diagnostics"
    ) {
        App()
    }
}
