package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.unit.dp

fun main() = application {
    val windows = remember { mutableStateListOf("Головне вікно") }

    // Render active dialog windows as separate OS windows
    activeDesktopDialogs.toList().forEach { dialogData ->
        Window(
            onCloseRequest = {
                dialogData.onDismiss()
                activeDesktopDialogs.remove(dialogData)
            },
            title = dialogData.title,
            state = rememberWindowState(width = 400.dp, height = 500.dp)
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                dialogData.content()
            }
        }
    }

    windows.toList().forEach { windowTitle ->
        Window(
            onCloseRequest = {
                windows.remove(windowTitle)
            },
            title = windowTitle,
            state = rememberWindowState(width = 800.dp, height = 600.dp)
        ) {
            MenuBar {
                Menu("Файл") {
                    Item(
                        "Нове вікно",
                        shortcut = KeyShortcut(Key.N, ctrl = true),
                        onClick = {
                            windows.add("Вікно ${windows.size + 1}")
                        }
                    )
                    Separator()
                    Item(
                        "Закрити",
                        shortcut = KeyShortcut(Key.W, ctrl = true),
                        onClick = {
                            windows.remove(windowTitle)
                        }
                    )
                    Item(
                        "Вийти",
                        shortcut = KeyShortcut(Key.Q, ctrl = true),
                        onClick = {
                            exitApplication()
                        }
                    )
                }
                Menu("Дії") {
                    Item(
                        "Відкрити діалог",
                        shortcut = KeyShortcut(Key.D, ctrl = true),
                        onClick = {
                            showDialog("Діалог з меню", onDismiss = {}) {
                                Surface(modifier = Modifier.padding(16.dp)) {
                                    Text("Привіт з діалогу (викликано через MenuBar)!")
                                }
                            }
                        }
                    )
                }
            }
            Surface(modifier = Modifier.fillMaxSize()) {
                App(
                    onNewWindowClick = {
                        windows.add("Вікно ${windows.size + 1}")
                    },
                    onOpenDialogClick = {
                        showDialog("Діалог з екрану", onDismiss = {}) {
                            Surface(modifier = Modifier.padding(16.dp)) {
                                Text("Привіт з діалогу (викликано з екрану)!")
                            }
                        }
                    }
                )
            }
        }
    }
}