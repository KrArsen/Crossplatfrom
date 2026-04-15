package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.components.ScreenScaffold
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.theme.AppTheme

@Composable
fun ButtonsScreen(onBackClick: () -> Unit) {
    ScreenScaffold(title = "Buttons", onBackClick = onBackClick) { padding ->
        ButtonsContent(padding = padding)
    }
}

@Composable
private fun ButtonsContent(padding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        Button(onClick = {}) { Text("Button") }
        OutlinedButton(onClick = {}) { Text("Outlined Button") }
        TextButton(onClick = {}) { Text("Text Button") }
        ElevatedButton(onClick = {}) { Text("Elevated Button") }
        FilledTonalButton(onClick = {}) { Text("Filled Tonal Button") }
    }
}

@Preview
@Composable
fun ButtonsScreenPreview() {
    AppTheme {
        ButtonsContent(PaddingValues(0.dp))
    }
}
