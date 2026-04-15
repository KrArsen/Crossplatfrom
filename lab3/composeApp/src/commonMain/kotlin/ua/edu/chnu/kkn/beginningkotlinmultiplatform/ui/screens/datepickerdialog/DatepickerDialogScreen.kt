package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.datepickerdialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.components.ScreenScaffold
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatepickerDialogScreen(onBackClick: () -> Unit) {
    ScreenScaffold(title = "Datepicker Dialog", onBackClick = onBackClick) { padding ->
        var showDialog by remember { mutableStateOf(false) }
        val datePickerState = rememberDatePickerState()
        var selectedDate by remember { mutableStateOf("No date selected") }

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = { showDialog = true }) {
                Text("Open Datepicker")
            }
            Text("Selected date: $selectedDate")
        }

        if (showDialog) {
            DatePickerDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(
                        onClick = {
                            selectedDate = datePickerState.selectedDateMillis?.let { millis ->
                                val date = Instant.fromEpochMilliseconds(millis)
                                    .toLocalDateTime(TimeZone.currentSystemDefault())
                                    .date
                                date.toString()
                            } ?: "No date selected"
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
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@Preview
@Composable
private fun DatepickerDialogScreenPreview() {
    AppTheme {
        ScreenScaffold(title = "Datepicker Dialog") { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text("Preview")
            }
        }
    }
}
