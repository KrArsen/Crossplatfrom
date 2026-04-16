package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.input.key.Key
import beginningkotlinmultiplatform.composeapp.generated.resources.Res
import beginningkotlinmultiplatform.composeapp.generated.resources.app_name
import beginningkotlinmultiplatform.composeapp.generated.resources.close
import beginningkotlinmultiplatform.composeapp.generated.resources.exit
import beginningkotlinmultiplatform.composeapp.generated.resources.file
import beginningkotlinmultiplatform.composeapp.generated.resources.new
import org.jetbrains.compose.resources.stringResource
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.shared_mobile.main.MainScreen

data class WindowInfo(val windowName: String, val windowState: WindowState)

fun main() = application {

    var initialized by remember { mutableStateOf(false) }
    var initialWindowNumber by remember { mutableStateOf(1) }
    var windowCount by remember { mutableStateOf(2) }
    val windowList = remember { SnapshotStateList<WindowInfo>() }
    val windowsName = stringResource(Res.string.app_name, initialWindowNumber)

    if (!initialized) {
        windowList.add(WindowInfo(windowsName, rememberWindowState()))
        initialized = true
    }

    windowList.forEachIndexed { i, _ ->
        Window(
            onCloseRequest = {
                if (windowList.size > 1) {
                    windowList.removeAt(i)
                } else {
                    exitApplication()
                }
            },
            state = windowList[i].windowState,
            title = windowList[i].windowName
        ) {
            val onNewWindow: () -> Unit = {
                windowCount++
                val newName = "Meeting's time finder $windowCount"
                windowList.add(
                    WindowInfo(
                        windowName = newName,
                        windowState = WindowState()
                    )
                )
            }

            MenuBar {
                Menu(stringResource(Res.string.file), mnemonic = 'F') {
                    Item(
                        stringResource(Res.string.new), onClick = {
                            onNewWindow()
                        }, shortcut = KeyShortcut(
                            Key.N, ctrl = true
                        )
                    )
                    Item(stringResource(Res.string.close), onClick = {
                        if (windowList.size > 1) {
                            windowList.removeAt(i)
                        } else {
                            exitApplication()
                        }
                    }, shortcut = KeyShortcut(Key.W, ctrl = true))
                    Separator()
                    Item(
                        stringResource(Res.string.exit),
                        onClick = { exitApplication() },
                        shortcut = KeyShortcut(Key.Q, ctrl = true)
                    )
                }
                Menu("View", mnemonic = 'V') {
                    Item(
                        "Refresh",
                        onClick = { /* trigger time refresh */ },
                        shortcut = KeyShortcut(Key.R, ctrl = true)
                    )
                }
                Menu("Edit", mnemonic = 'E') {
                    Item(
                        "Cut", onClick = { }, shortcut = KeyShortcut(
                            Key.X, ctrl = true
                        )
                    )
                    Item(
                        "Copy", onClick = { }, shortcut = KeyShortcut(
                            Key.C, ctrl = true
                        )
                    )
                    Item("Paste", onClick = { }, shortcut = KeyShortcut(Key.V, ctrl = true))
                }
            }
            Surface(modifier = Modifier.fillMaxSize()) {
                MainScreen(onNewWindow = onNewWindow)
            }
        }
    }
}