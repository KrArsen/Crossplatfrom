package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.shared.dialogs

import androidx.compose.runtime.Composable

@Composable
expect fun PlatformDialog(
    title: String,
    onCloseRequest: () -> Unit,
    content: @Composable () -> Unit
)
