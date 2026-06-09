package com.example.lab6

import androidx.compose.runtime.Composable

data class SystemInfo(
    val osName: String,
    val osVersion: String,
    val deviceModel: String
)

expect fun getSystemInfo(): SystemInfo

@Composable
expect fun getAboutViewModel(): AboutViewModel
