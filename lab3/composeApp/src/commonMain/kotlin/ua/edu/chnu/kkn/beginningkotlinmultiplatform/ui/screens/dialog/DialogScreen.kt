package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.components.ScreenScaffold
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.theme.AppTheme

@Composable
fun DialogScreen(onBackClick: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    ScreenScaffold(title = "Dialog", onBackClick = onBackClick) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = { showDialog = true }) {
                Text("Open Dialog")
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Demo Dialog") },
                text = { Text("This is a Material 3 AlertDialog example.") },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Dismiss")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun DialogScreenPreview() {
    AppTheme {
        DialogScreen(onBackClick = {})
    }
}
