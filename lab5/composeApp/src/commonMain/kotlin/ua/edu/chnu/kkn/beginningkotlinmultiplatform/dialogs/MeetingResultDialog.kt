package ua.edu.chnu.kkn.beginningkotlinmultiplatform.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.viewmodel.MeetingSlot

@Composable
fun MeetingResultDialog(
    slots: List<MeetingSlot>,
    selectedZones: List<String>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Suitable meeting times") },
        text = {
            if (slots.isEmpty()) {
                Text("No suitable time found for all selected timezones.")
            } else {
                LazyColumn(modifier = Modifier.heightIn(max = 420.dp)) {
                    items(slots) { slot ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(
                                "UTC ${slot.utcHour.toString().padStart(2, '0')}:00",
                                style = MaterialTheme.typography.titleMedium
                            )
                            selectedZones.forEach { zoneId ->
                                val zoneTime = slot.timesPerZone[zoneId] ?: "--:--"
                                Text("$zoneId: $zoneTime")
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}
