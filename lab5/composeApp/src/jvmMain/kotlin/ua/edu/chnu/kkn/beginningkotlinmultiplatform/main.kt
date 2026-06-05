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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.unit.dp

fun main() = application {
    val windows = remember { mutableStateListOf("Головне вікно") }

    windows.forEachIndexed { index, windowTitle ->
        Window(
            onCloseRequest = {
                windows.removeAt(index)
            },
            title = windowTitle
        ) {
            MenuBar {
                Menu("Файл", mnemonic = 'Ф') {
                    Item(
                        "Нове вікно",
                        shortcut = KeyShortcut(Key.N, ctrl = true),
                        onClick = {
                            windows.add("Вікно ${windows.size + 1}")
                        }
                    )
                    Separator()
                    Item(
                        "Вийти",
                        shortcut = KeyShortcut(Key.Q, ctrl = true),
                        onClick = {
                            exitApplication()
                        }
                    )
                }
                Menu("Дія", mnemonic = 'Д') {
                    Item(
                        "Відкрити діалог",
                        shortcut = KeyShortcut(Key.D, ctrl = true),
                        onClick = {
                            openDialog("Діалог з меню") {
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
                        openDialog("Діалог з екрану") {
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