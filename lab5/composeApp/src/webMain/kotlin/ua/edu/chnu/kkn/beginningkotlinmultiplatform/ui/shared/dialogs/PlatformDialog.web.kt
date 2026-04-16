package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.shared.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
actual fun PlatformDialog(
    title: String,
    onCloseRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f))) {
        Surface(modifier = Modifier.align(Alignment.Center).padding(16.dp)) {
            Column {
                Text(title)
                content()
                Button(onClick = onCloseRequest) { Text("Close") }
            }
        }
    }
}
