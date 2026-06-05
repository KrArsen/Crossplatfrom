package com.example.lab6

actual fun getSystemInfo(): SystemInfo = SystemInfo(
    osName = System.getProperty("os.name") ?: "Desktop (JVM)",
    osVersion = System.getProperty("os.version") ?: "Unknown",
    deviceModel = "${System.getProperty("os.arch")} JVM (${System.getProperty("java.vendor")} ${System.getProperty("java.version")})"
)
