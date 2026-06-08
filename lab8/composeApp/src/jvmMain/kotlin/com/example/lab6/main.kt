package com.example.lab6

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.context.startKoin
import com.example.lab6.di.appModule

fun main() {
    startKoin {
        modules(appModule)
    }
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Lab 8: System Diagnostics"
        ) {
            App()
        }
    }
}
