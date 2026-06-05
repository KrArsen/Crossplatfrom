package com.example.lab6

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AboutUiState(
    val appName: String = "",
    val version: String = "",
    val author: String = "",
    val description: String = "",
    val osName: String = "",
    val osVersion: String = "",
    val deviceModel: String = ""
)

class AboutViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AboutUiState(
        appName = "Diagnostics App",
        version = "1.0.0",
        author = "Student",
        description = "Kotlin Multiplatform application with dynamic Material3 theme and Kermit logger.",
        osName = getSystemInfo().osName,
        osVersion = getSystemInfo().osVersion,
        deviceModel = getSystemInfo().deviceModel
    ))
    val uiState: StateFlow<AboutUiState> = _uiState
}
