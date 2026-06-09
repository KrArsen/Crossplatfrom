package com.example.lab6

import android.os.Build
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

actual fun getSystemInfo(): SystemInfo = SystemInfo(
    osName = "Android",
    osVersion = "${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})",
    deviceModel = "${Build.MANUFACTURER} ${Build.MODEL}"
)

@Composable
actual fun getAboutViewModel(): AboutViewModel = koinViewModel()

