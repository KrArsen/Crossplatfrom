package com.example.lab6

import androidx.lifecycle.ViewModel
import com.example.lab6.repository.AboutRepository
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

class AboutViewModel(
    private val repository: AboutRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AboutUiState(
        appName = repository.getAppName(),
        version = repository.getVersion(),
        author = repository.getAuthor(),
        description = repository.getDescription(),
        osName = getSystemInfo().osName,
        osVersion = getSystemInfo().osVersion,
        deviceModel = getSystemInfo().deviceModel
    ))
    val uiState: StateFlow<AboutUiState> = _uiState
}
