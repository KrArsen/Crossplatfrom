package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.timepickerdialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimepickerDialogScreen(onBackClick: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()
    var selectedTime by remember { mutableStateOf("No time selected") }

    ScreenScaffold(title = "Timepicker Dialog", onBackClick = onBackClick) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = { showDialog = true }) {
                Text("Open Timepicker")
            }
            Text("Selected time: $selectedTime")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Select time") },
                text = {
                    TimePicker(state = timePickerState)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            selectedTime = "%02d:%02d".format(timePickerState.hour, timePickerState.minute)
                            showDialog = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun TimepickerDialogScreenPreview() {
    AppTheme {
        TimepickerDialogScreen(onBackClick = {})
    }
}
