package com.example.lab6

import android.os.Build

actual fun getSystemInfo(): SystemInfo = SystemInfo(
    osName = "Android",
    osVersion = "${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})",
    deviceModel = "${Build.MANUFACTURER} ${Build.MODEL}"
)
