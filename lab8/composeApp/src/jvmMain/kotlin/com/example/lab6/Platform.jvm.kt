package com.example.lab6

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.core.context.GlobalContext

actual fun getSystemInfo(): SystemInfo = SystemInfo(
    osName = System.getProperty("os.name") ?: "Desktop (JVM)",
    osVersion = System.getProperty("os.version") ?: "Unknown",
    deviceModel = "${System.getProperty("os.arch")} JVM (${System.getProperty("java.vendor")} ${System.getProperty("java.version")})"
)

@Composable
actual fun getAboutViewModel(): AboutViewModel = remember {
    GlobalContext.get().get()
}

