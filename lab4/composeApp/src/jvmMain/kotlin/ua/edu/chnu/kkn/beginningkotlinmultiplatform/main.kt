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
                windowList.removeAt(i)
            },
            state = windowList[i].windowState,
            title = windowList[i].windowName
        ) {
            MenuBar {
                Menu(stringResource(Res.string.file), mnemonic = 'F') {
                    val windowsName = stringResource(Res.string.app_name, windowCount)
                    val nextWindowState = rememberWindowState()
                    Item(
                        stringResource(Res.string.new), onClick = {
                            windowCount++
                            windowList.add(
                                WindowInfo(
                                    windowName = windowsName,
                                    nextWindowState
                                )
                            )
                        }, shortcut = KeyShortcut(
                            Key.N, ctrl = true
                        )
                    )
                    Item(stringResource(Res.string.close), onClick = {
                        windowList.removeAt(i)
                    }, shortcut = KeyShortcut(Key.W, ctrl = true))
                    Separator()
                    Item(
                        stringResource(Res.string.exit),
                        onClick = { windowList.clear() },
                        shortcut = KeyShortcut(Key.Q, ctrl = true)
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
                App()
            }
        }
    }
}