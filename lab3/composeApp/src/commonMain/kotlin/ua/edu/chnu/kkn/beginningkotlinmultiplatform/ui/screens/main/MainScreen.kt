package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.navigation.Screen
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.components.ScreenScaffold
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.theme.AppTheme

@Composable
fun MainScreen(
    onNavigate: (Screen) -> Unit
) {
    val entries = listOf(
        "Buttons" to Screen.Buttons,
        "Checkboxes" to Screen.Checkboxes,
        "Chips" to Screen.Chips,
        "Datepicker Dialog" to Screen.DatepickerDialog,
        "Dialog" to Screen.Dialog,
        "Divider" to Screen.Divider,
        "Progress Bar" to Screen.ProgressBar,
        "Radio Buttons" to Screen.RadioButtons,
        "Switch" to Screen.Switch,
        "Timepicker Dialog" to Screen.TimepickerDialog
    )

    ScreenScaffold(title = "Home") { padding ->
        MainMenuContent(
            entries = entries,
            padding = padding,
            onNavigate = onNavigate
        )
    }
}

@Composable
private fun MainMenuContent(
    entries: List<Pair<String, Screen>>,
    padding: PaddingValues,
    onNavigate: (Screen) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(padding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(entries) { entry ->
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onNavigate(entry.second) }
            ) {
                Text(entry.first)
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    AppTheme {
        MainScreen(onNavigate = {})
    }
}